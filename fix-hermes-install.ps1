<#
.SYNOPSIS
    Hermes Install Fix Script
.DESCRIPTION
    Fixes permission and Git network issues for Hermes installation on Windows.
#>

$ErrorActionPreference = 'Stop'
$LogFile = Join-Path $env:TEMP "hermes-fix-$(Get-Date -Format 'yyyyMMdd-HHmmss').log"

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
Write-Host "     Hermes Install Fix Script" -ForegroundColor Cyan
Write-Host "     Log: $LogFile" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$isAdmin = Test-Admin
if (-not $isAdmin) {
    Write-Host "[WARNING] Not running as Administrator." -ForegroundColor Yellow
    Write-Host "          Some operations (e.g., system PATH) may fail." -ForegroundColor Yellow
    Write-Host "          Right-click PowerShell -> Run as Administrator if needed." -ForegroundColor Yellow
    Write-Host ""
}

# Step 1: Configure Git for better network stability
Write-Log "[Step 1/4] Configuring Git for stable network..." -ForegroundColor Yellow

# Increase HTTP buffer to 500MB
git config --global http.postBuffer 524288000
Write-Log "  OK http.postBuffer = 524288000 (500MB)" -ForegroundColor Green

# Downgrade to HTTP/1.1 to avoid HTTP/2 CANCEL issues
git config --global http.version HTTP/1.1
Write-Log "  OK http.version = HTTP/1.1" -ForegroundColor Green

git config --global http.sslVerify true
Write-Log "  OK http.sslVerify = true" -ForegroundColor Green

git config --global core.compression 9
Write-Log "  OK core.compression = 9" -ForegroundColor Green

git config --global http.lowSpeedLimit 0
git config --global http.lowSpeedTime 999999
Write-Log "  OK low speed timeout disabled" -ForegroundColor Green

git config --global http.timeout 300
Write-Log "  OK http.timeout = 300s" -ForegroundColor Green

Write-Log "  Git configuration complete" -ForegroundColor Green

# Step 2: Show current Git config
Write-Log "[Step 2/4] Current Git config:" -ForegroundColor Cyan
git config --global --list | ForEach-Object { Write-Log "  $_" }

# Step 3: Clone/update repository
Write-Log "[Step 3/4] Handling repository..." -ForegroundColor Yellow
$RepoDir = "$env:LOCALAPPDATA\hermes\hermes-agent"
$RepoUrl = "https://github.com/NousResearch/hermes-agent.git"

Write-Log "  Target: $RepoDir" -ForegroundColor Cyan

$hasExistingRepo = Test-Path (Join-Path $RepoDir ".git")

if ($hasExistingRepo) {
    Write-Log "  Existing repo found, updating..." -ForegroundColor Yellow
    Push-Location $RepoDir
    try {
        & git -c safe.directory=* config http.version HTTP/1.1
        & git -c safe.directory=* config http.postBuffer 524288000

        # Clean lock files
        $lockFiles = @(".git/index.lock", ".git/HEAD.lock", ".git/fetch_head.lock", ".git/sharded_index.lock")
        foreach ($lf in $lockFiles) {
            if (Test-Path $lf) { Remove-Item $lf -Force }
        }

        # Stash local changes if any
        $oldPref = $ErrorActionPreference
        $ErrorActionPreference = 'Continue'
        $status = & git -c safe.directory=* status --porcelain
        $ErrorActionPreference = $oldPref
        if ($status) {
            Write-Log "  Stashing local changes..." -ForegroundColor Yellow
            & git -c safe.directory=* stash push -m "hermes-fix-autostash-$(Get-Date -Format 'yyyyMMdd-HHmmss')"
        }

        # Try fetching with retries
        $maxRetries = 3
        $fetchOk = $false
        for ($i = 1; $i -le $maxRetries; $i++) {
            Write-Log "  Fetch attempt #$i ..." -ForegroundColor Yellow
            # Use & with null stderr redirect to avoid PowerShell error handling
            $oldPref = $ErrorActionPreference
            $ErrorActionPreference = 'Continue'
            $null = & git -c safe.directory=* fetch --depth=50 origin main 2>&1
            $ec = $LASTEXITCODE
            $ErrorActionPreference = $oldPref
            if ($ec -eq 0) {
                $fetchOk = $true
                Write-Log "  OK Fetch succeeded" -ForegroundColor Green
                break
            }
            Write-Log "  WARN Fetch failed (exit $ec), retrying in 5s..." -ForegroundColor Red
            Start-Sleep -Seconds 5
        }

        if ($fetchOk) {
            $oldPref = $ErrorActionPreference
            $ErrorActionPreference = 'Continue'
            $null = & git -c safe.directory=* fetch --unshallow 2>&1
            & git -c safe.directory=* merge origin/main 2>&1 | Out-Null
            $ErrorActionPreference = $oldPref
            Write-Log "  OK Repo updated" -ForegroundColor Green
        } else {
            Write-Log "  WARN All fetches failed, skipping update" -ForegroundColor Yellow
        }
    }
    finally {
        Pop-Location
    }
} else {
    Write-Log "  No repo found, cloning..." -ForegroundColor Yellow

    $parentDir = Split-Path $RepoDir -Parent
    if (-not (Test-Path $parentDir)) {
        New-Item -ItemType Directory -Path $parentDir -Force | Out-Null
    }

    $cloned = $false
    for ($i = 1; $i -le 3; $i++) {
        Write-Log "  Clone attempt #$i (depth=50)..." -ForegroundColor Yellow
        if (Test-Path $RepoDir) {
            Remove-Item $RepoDir -Recurse -Force
        }
        $oldPref = $ErrorActionPreference
        $ErrorActionPreference = 'Continue'
        & git -c safe.directory=* clone --depth=50 --branch main $RepoUrl $RepoDir 2>&1
        $ec = $LASTEXITCODE
        $ErrorActionPreference = $oldPref
        if ($ec -eq 0) {
            $cloned = $true
            Write-Log "  OK Clone succeeded" -ForegroundColor Green
            break
        }
        Write-Log "  WARN Clone failed, retrying in 5s..." -ForegroundColor Red
        Start-Sleep -Seconds 5
    }

    if (-not $cloned) {
        Write-Log "  FAILED All clone attempts failed" -ForegroundColor Red
        Write-Log "  Please run manually:" -ForegroundColor Yellow
        Write-Log "    git clone --depth=50 --branch main $RepoUrl `"$RepoDir`"" -ForegroundColor Yellow
        exit 1
    }
}

# Step 4: Find and run Hermes installer
Write-Log "[Step 4/4] Launching Hermes installer..." -ForegroundColor Yellow

$possibleInstallers = @(
    "$env:LOCALAPPDATA\hermes\bootstrap-cache\install-main.ps1",
    "$env:LOCALAPPDATA\hermes\hermes-agent\install.ps1",
    "$env:LOCALAPPDATA\hermes\hermes-agent\scripts\install.ps1",
    "$env:LOCALAPPDATA\hermes\hermes-agent\bootstrap.ps1"
)

$installerPath = $null
foreach ($p in $possibleInstallers) {
    if (Test-Path $p) {
        $installerPath = $p
        break
    }
}

if ($installerPath) {
    Write-Log "  Found installer: $installerPath" -ForegroundColor Green
    Write-Log "  Starting installer (may take a long time)..." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "----------------------------------------" -ForegroundColor Magenta
    Write-Host "  Hermes installer is running..." -ForegroundColor Magenta
    Write-Host "  Please wait and do not close this window." -ForegroundColor Magenta
    Write-Host "----------------------------------------" -ForegroundColor Magenta
    Write-Host ""
    & $installerPath
}
else {
    Write-Log "  Installer script not found, trying uv..." -ForegroundColor Yellow
    $uvPath = "$env:LOCALAPPDATA\hermes\bin\uv.exe"
    if (Test-Path $uvPath) {
        Write-Log "  Running via uv..." -ForegroundColor Yellow
        & $uvPath run --project "$RepoDir" hermes install
    } else {
        Write-Log "  ERROR Cannot find installer." -ForegroundColor Red
        Write-Log "  Run manually as Admin: hermes install" -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Log "  Script finished!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
pause
