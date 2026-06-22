<script lang='ts' setup>
import {onMounted, reactive, ref, toRefs} from 'vue'
import {useStore} from '@/stores'
import {useRouter} from 'vue-router'
import {getArticle} from '@/api/Article'
import {queryManyUser} from '@/api/User'
import {ElMessage} from 'element-plus'

const store = useStore()
const router = useRouter()

const data = reactive({
  // 统计数字
  articleCount: 0,
  userCount: 0,
  tagCount: 0,
  viewCount: 0,
  // 最近文章
  recentArticles: [] as any[],
  // 加载状态
  loading: true,
})

// 当前时间问候
const greeting = ref('')
const currentTime = ref('')

function setGreeting() {
  const hour = new Date().getHours()
  if (hour < 6) greeting.value = '夜深了'
  else if (hour < 9) greeting.value = '早上好'
  else if (hour < 12) greeting.value = '上午好'
  else if (hour < 14) greeting.value = '中午好'
  else if (hour < 18) greeting.value = '下午好'
  else greeting.value = '晚上好'
  currentTime.value = new Date().toLocaleString('zh-CN', {
    year: 'numeric', month: 'long', day: 'numeric',
    weekday: 'long', hour: '2-digit', minute: '2-digit',
  })
}

// 用户信息
const userInfo = ref<any>({})

async function fetchStats() {
  try {
    // 文章总数
    const articlesRes = await getArticle(1, 1)
    if (articlesRes?.code === 200) {
      data.articleCount = articlesRes.result?.total || 0
      // 同时拿最近 5 篇文章
      const allRes = await getArticle(1, 5)
      if (allRes?.code === 200) {
        data.recentArticles = allRes.result?.list || []
        // 累计阅读量
        data.viewCount = data.recentArticles.reduce(
          (sum: number, a: any) => sum + (a.whatchNumber || 0), 0
        )
      }
    }
    // 用户总数
    const userRes = await queryManyUser(1, 1)
    if (userRes?.code === 200) {
      data.userCount = userRes.result?.total || 0
    }
    // 标签总数（tags 接口返回全部列表）
    const {queryManyTag} = await import('@/api/Tag')
    const tagRes = await queryManyTag(0, 0)
    if (tagRes?.code === 200) {
      data.tagCount = Array.isArray(tagRes.result) ? tagRes.result.length : 0
    }
  } catch (e) {
    console.error('获取统计数据失败', e)
  } finally {
    data.loading = false
  }
}

function goTo(path: string) {
  router.push(path)
}

function formatDate(dateStr: string) {
  if (!dateStr) return '-'
  const d = new Date(dateStr)
  return d.toLocaleDateString('zh-CN')
}

onMounted(() => {
  setGreeting()
  userInfo.value = store.userInfo || {}
  fetchStats()
})

const {articleCount, userCount, tagCount, viewCount, recentArticles, loading} = {
  ...toRefs(data)
}
</script>

<template>
  <div class="dashboard">
    <!-- 欢迎横幅 -->
    <el-card class="welcome-card" shadow="never">
      <div class="welcome-content">
        <div class="welcome-left">
          <h1 class="greeting">{{ greeting }}，{{ (userInfo as any)?.user?.nickName || '管理员' }}</h1>
          <p class="subtitle">欢迎回到博客管理系统，今天是 {{ currentTime }}</p>
        </div>
        <div class="welcome-right">
          <div class="welcome-icon">
            <svg viewBox="0 0 24 24" width="64" height="64" fill="none" stroke="currentColor" stroke-width="1.5">
              <path d="M3 9.5L12 4l9 5.5v8a1 1 0 01-1 1H4a1 1 0 01-1-1v-8z"/>
              <path d="M9 22V12h6v10"/>
            </svg>
          </div>
        </div>
      </div>
    </el-card>

    <!-- 统计卡片 -->
    <div class="stats-grid" v-loading="loading">
      <el-card class="stat-card stat-article" shadow="hover" @click="goTo('/article')">
        <div class="stat-inner">
          <div class="stat-icon">
            <el-icon :size="28"><Document /></el-icon>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ articleCount }}</span>
            <span class="stat-label">文章总数</span>
          </div>
        </div>
      </el-card>

      <el-card class="stat-card stat-user" shadow="hover" @click="goTo('/user')">
        <div class="stat-inner">
          <div class="stat-icon">
            <el-icon :size="28"><UserFilled /></el-icon>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ userCount }}</span>
            <span class="stat-label">用户总数</span>
          </div>
        </div>
      </el-card>

      <el-card class="stat-card stat-tag" shadow="hover" @click="goTo('/tag')">
        <div class="stat-inner">
          <div class="stat-icon">
            <el-icon :size="28"><CollectionTag /></el-icon>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ tagCount }}</span>
            <span class="stat-label">标签总数</span>
          </div>
        </div>
      </el-card>

      <el-card class="stat-card stat-view" shadow="hover">
        <div class="stat-inner">
          <div class="stat-icon">
            <el-icon :size="28"><View /></el-icon>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ viewCount }}</span>
            <span class="stat-label">累计阅读</span>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 最近文章 -->
    <el-card class="recent-card" shadow="never">
      <template #header>
        <div class="card-header">
          <span class="card-title">📄 最近文章</span>
          <el-button text type="primary" @click="goTo('/article')">查看全部 →</el-button>
        </div>
      </template>
      <el-table :data="recentArticles" stripe style="width: 100%" v-loading="loading">
        <el-table-column prop="name" label="标题" min-width="200" show-overflow-tooltip />
        <el-table-column prop="tag" label="标签" width="120">
          <template #default="{ row }">
            <el-tag v-if="row.tag" size="small">{{ row.tag }}</el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="like" label="点赞" width="80" align="center" />
        <el-table-column prop="whatchNumber" label="阅读" width="80" align="center" />
        <el-table-column label="创建时间" width="160" align="center">
          <template #default="{ row }">{{ formatDate(row.createdAt) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="100" align="center">
          <template #default="{ row }">
            <el-button text type="primary" size="small" @click="goTo('/article')">管理</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-if="!loading && recentArticles.length === 0" description="暂无文章" :image-size="80" />
    </el-card>
  </div>
</template>

<style lang='scss' scoped>
.dashboard {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

/* 欢迎卡片 */
.welcome-card {
  border-radius: 12px;
  margin-bottom: 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  border: none;

  :deep(.el-card__body) {
    padding: 28px 32px;
  }
}

.welcome-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.welcome-left {
  .greeting {
    font-size: 26px;
    font-weight: 700;
    margin: 0 0 8px;
    letter-spacing: 1px;
  }

  .subtitle {
    font-size: 14px;
    opacity: 0.85;
    margin: 0;
  }
}

.welcome-right {
  .welcome-icon {
    opacity: 0.6;
    svg { display: block; }
  }
}

/* 统计卡片 */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}

.stat-card {
  border-radius: 12px;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;

  &:hover {
    transform: translateY(-4px);
  }

  :deep(.el-card__body) {
    padding: 20px 24px;
  }

  .stat-inner {
    display: flex;
    align-items: center;
    gap: 16px;
  }

  .stat-icon {
    width: 52px;
    height: 52px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    flex-shrink: 0;
  }

  .stat-info {
    display: flex;
    flex-direction: column;

    .stat-value {
      font-size: 28px;
      font-weight: 700;
      line-height: 1.2;
      color: #303133;
    }

    .stat-label {
      font-size: 13px;
      color: #909399;
      margin-top: 4px;
    }
  }
}

.stat-article .stat-icon { background: linear-gradient(135deg, #409eff, #337ecc); }
.stat-user   .stat-icon { background: linear-gradient(135deg, #67c23a, #529b2e); }
.stat-tag    .stat-icon { background: linear-gradient(135deg, #e6a23c, #cf9236); }
.stat-view   .stat-icon { background: linear-gradient(135deg, #f56c6c, #d95959); }

/* 最近文章卡片 */
.recent-card {
  border-radius: 12px;

  :deep(.el-card__header) {
    border-bottom: 1px solid #ebeef5;
    padding: 16px 24px;
  }

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .card-title {
      font-size: 16px;
      font-weight: 600;
      color: #303133;
    }
  }
}

/* 响应式 */
@media (max-width: 900px) {
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 600px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }

  .welcome-right { display: none; }
}
</style>
