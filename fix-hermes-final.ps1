<#
.SYNOPSIS
    Hermes final fix - cleanup old repo and run installer
#>

$ErrorActionPreference = 'Stop'
$LogFile = Join-Path $env:TEMP "hermes-final-$(Get-Date -Format 'yyyyMMdd-HHmmss').log"
$RepoDir = "$env:LOCALAPPDATA\hermes\hermes-agent"

function Write-Log {
    param([string]$Message, [string]$ForegroundColor = 'White')
    $time = Get-Date -Format 'HH:mm:ss'
    Write-Host "[$time] $Message" -ForegroundColor $ForegroundColor
    "[$time] $Message" | Out-File -FilePath $LogFile -Encoding utf8 -Append
}

function Test-Admin {
    $identity = [Security.Principal.WindowsIdentity]::GetCurrent()
    $principal = New-Object Security.Principal.WindowsPrincipal($identity)
    return $principal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Hermes Final Setup Script" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$isAdmin = Test-Admin
if (-not $isAdmin) {
    Write-Host "[WARNING] Not running as Administrator." -ForegroundColor Yellow
    Write-Host "          Run PowerShell as Admin for best results." -ForegroundColor Yellow
}

# Step 1: Kill any processes using the repo directory
Write-Log "[1/4] Closing processes that may lock the repo directory..." -ForegroundColor Yellow

# Kill any git processes
Get-Process git, git.exe, ssh-agent, ssh -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue
Write-Log "  OK Stopped git/ssh processes" -ForegroundColor Green

# Step 2: Forcefully remove old repo directory
Write-Log "[2/4] Removing old repo directory..." -ForegroundColor Yellow

if (Test-Path $RepoDir) {
    # First try normal remove
    try {
        Remove-Item $RepoDir -Recurse -Force -ErrorAction Stop
        Write-Log "  OK Removed: $RepoDir" -ForegroundColor Green
    }
    catch {
        Write-Log "  Normal removal failed, trying takeown..." -ForegroundColor Yellow
        # Retry with takeown (needs admin)
        try {
            takeown /F $RepoDir /R /D Y 2>$null | Out-Null
            icacls $RepoDir /grant "${env:USERNAME}:F" /T /Q 2>$null | Out-Null
            Remove-Item $RepoDir -Recurse -Force -ErrorAction Stop
            Write-Log "  OK Removed with takeown: $RepoDir" -ForegroundColor Green
        }
        catch {
            Write-Log "  WARN Could not fully remove directory." -ForegroundColor Red
            Write-Log "  Please close all programs (editors, terminals, file explorer)" -ForegroundColor Yellow
            Write-Log "  and run this script again." -ForegroundColor Yellow
            pause
            exit 1
        }
    }
}
else {
    Write-Log "  No directory to remove." -ForegroundColor Green
}

# Also remove broken backup if exists
$brokenDir = "$env:LOCALAPPDATA\hermes\hermes-agent.broken-*"
$brokenItems = Get-ChildItem $brokenDir -ErrorAction SilentlyContinue
foreach ($item in $brokenItems) {
    try { Remove-Item $item.FullName -Recurse -Force } catch {}
}
Write-Log "  OK Cleaned up broken backup directories" -ForegroundColor Green

# Step 3: Ensure Git config is set for stable download
Write-Log "[3/4] Verifying Git config..." -ForegroundColor Yellow
git config --global http.postBuffer 524288000
git config --global http.version HTTP/1.1
git config --global http.lowSpeedLimit 0
git config --global http.lowSpeedTime 999999
git config --global http.timeout 300
Write-Log "  OK Git configured for stable download" -ForegroundColor Green

# Step 4: Run Hermes installer directly
Write-Log "[4/4] Running Hermes installer..." -ForegroundColor Yellow

$installerPath = "$env:LOCALAPPDATA\hermes\bootstrap-cache\install-main.ps1"
if (-not (Test-Path $installerPath)) {
    Write-Log "  Downloading fresh installer..." -ForegroundColor Yellow
    Invoke-WebRequest -Uri 'https://hermes-agent.nousresearch.com/install.ps1' -OutFile "$env:TEMP\hermes-install.ps1" -ErrorAction Stop
    $installerPath = "$env:TEMP\hermes-install.ps1"
}

Write-Log "  Installer: $installerPath" -ForegroundColor Green
Write-Host ""
Write-Host "----------------------------------------" -ForegroundColor Magenta
Write-Host "  Hermes installer starting..." -ForegroundColor Magenta
Write-Host "  THIS MAY TAKE A LONG TIME (10-30 min)" -ForegroundColor Magenta
Write-Host "  Do NOT close this window." -ForegroundColor Magenta
Write-Host "----------------------------------------" -ForegroundColor Magenta
Write-Host ""

# Ensure execution policy allows running
$oldPref = $ErrorActionPreference
$ErrorActionPreference = 'Continue'
& $installerPath
$ec = $LASTEXITCODE
$ErrorActionPreference = $oldPref

Write-Host ""
if ($ec -eq 0) {
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "  HERMES INSTALLATION COMPLETE!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
}
else {
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "  Installer finished with exit code: $ec" -ForegroundColor Red
    Write-Host "  Check the log: $LogFile" -ForegroundColor Yellow
    Write-Host "========================================" -ForegroundColor Red
}
pause
