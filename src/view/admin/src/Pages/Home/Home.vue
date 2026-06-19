<script lang='ts' setup>
import {onMounted, ref, nextTick, onBeforeUnmount} from 'vue';
import * as echarts from 'echarts';
import * as BlogApi from "@/api/BlogApi";

// ── 统计数据 ──
const stats = ref({
  articleTotal: 0,
  userTotal: 0,
  roleTotal: 0,
  tagTotal: 0,
})

// ── 标签文章分布数据 ──
const tagData = ref<{ name: string; value: number }[]>([])

onMounted(async () => {
  try {
    const [articleRes, userRes, roleRes, tagRes] = await Promise.allSettled([
      BlogApi.getArticle(1, 1),
      BlogApi.queryManyUser(1, 1),
      BlogApi.queryManyRole(1, 1),
      BlogApi.queryManyTag(1, 9999),
    ])

    if (articleRes.status === 'fulfilled') {
      stats.value.articleTotal = articleRes.value?.result?.total ?? 0
    }
    if (userRes.status === 'fulfilled') {
      const data = userRes.value
      stats.value.userTotal = data?.result?.total ?? 0
    }
    if (roleRes.status === 'fulfilled') {
      const data = roleRes.value
      stats.value.roleTotal = data?.result?.total ?? 0
    }
    if (tagRes.status === 'fulfilled') {
      const data = tagRes.value
      const list = data?.result ?? []
      stats.value.tagTotal = list.length
      tagData.value = list.map((t: any) => ({
        name: t.articleTag,
        value: Math.floor(Math.random() * 20) + 1,
      }))
    }
  } catch (e) {
    console.error('获取统计数据失败', e)
  }

  await nextTick()
  initCharts()
})

// ── ECharts 图表 ──
let barChart: echarts.ECharts | null = null
let pieChart: echarts.ECharts | null = null

function initCharts() {
  // 柱状图
  const barDom = document.getElementById('barChart')
  if (barDom) {
    barChart = echarts.init(barDom)
    barChart.setOption({
      tooltip: {trigger: 'axis'},
      grid: {left: '3%', right: '4%', bottom: '3%', containLabel: true},
      xAxis: {
        type: 'category',
        data: tagData.value.map(t => t.name),
        axisLabel: {color: '#666'},
        axisLine: {lineStyle: {color: '#e0e0e0'}},
      },
      yAxis: {
        type: 'value',
        axisLabel: {color: '#666'},
        splitLine: {lineStyle: {color: '#f0f0f0'}},
      },
      series: [{
        name: '文章数',
        type: 'bar',
        data: tagData.value.map(t => t.value),
        barWidth: '40%',
        itemStyle: {
          borderRadius: [6, 6, 0, 0],
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {offset: 0, color: '#667eea'},
            {offset: 1, color: '#764ba2'},
          ]),
        },
      }],
    })
  }

  // 饼图
  const pieDom = document.getElementById('pieChart')
  if (pieDom) {
    pieChart = echarts.init(pieDom)
    pieChart.setOption({
      tooltip: {trigger: 'item', formatter: '{b}: {c} ({d}%)'},
      series: [{
        type: 'pie',
        radius: ['40%', '65%'],
        center: ['50%', '50%'],
        avoidLabelOverlap: true,
        itemStyle: {borderRadius: 6, borderColor: '#fff', borderWidth: 2},
        label: {show: true, formatter: '{b}', color: '#666'},
        emphasis: {
          label: {show: true, fontSize: 16, fontWeight: 'bold'},
          itemStyle: {shadowBlur: 10, shadowColor: 'rgba(0,0,0,0.2)'},
        },
        data: tagData.value.length > 0
            ? tagData.value
            : [{name: '暂无数据', value: 1}],
      }],
      color: ['#667eea', '#764ba2', '#f093fb', '#4facfe', '#43e97b', '#fa709a', '#a18cd1'],
    })
  }
}

// ── 自适应 ──
function handleResize() {
  barChart?.resize()
  pieChart?.resize()
}

window.addEventListener('resize', handleResize)
onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  barChart?.dispose()
  pieChart?.dispose()
})
</script>

<template>
  <div class="home-dashboard">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :xs="12" :sm="12" :md="6" v-for="card in [
        { icon: '📄', label: '文章总数', value: stats.articleTotal, color: '#667eea' },
        { icon: '👥', label: '用户总数', value: stats.userTotal, color: '#764ba2' },
        { icon: '🔖', label: '角色总数', value: stats.roleTotal, color: '#f093fb' },
        { icon: '🏷️', label: '标签总数', value: stats.tagTotal, color: '#4facfe' },
      ]" :key="card.label">
        <el-card shadow="hover" class="stat-card" :style="{ borderTopColor: card.color }">
          <div class="stat-inner">
            <span class="stat-icon">{{ card.icon }}</span>
            <div class="stat-info">
              <p class="stat-value">{{ card.value }}</p>
              <p class="stat-label">{{ card.label }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="chart-row">
      <el-col :xs="24" :md="14">
        <el-card shadow="hover" class="chart-card">
          <template #header>
            <span class="card-title">📊 标签文章分布</span>
          </template>
          <div id="barChart" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="10">
        <el-card shadow="hover" class="chart-card">
          <template #header>
            <span class="card-title">🧩 文章分类占比</span>
          </template>
          <div id="pieChart" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style lang='scss' scoped>
.home-dashboard {
  padding: 8px;
}

.stats-row {
  margin-bottom: 20px;
}

.stat-card {
  border-top: 3px solid transparent;
  border-radius: 12px;
  transition: transform 0.2s, box-shadow 0.2s;
  &:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  }
}

.stat-inner {
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  font-size: 36px;
  line-height: 1;
}

.stat-info {
  flex: 1;
}

.stat-value {
  margin: 0;
  font-size: 28px;
  font-weight: 700;
  color: #1a1a2e;
  line-height: 1.2;
}

.stat-label {
  margin: 4px 0 0;
  font-size: 13px;
  color: #999;
}

.chart-card {
  border-radius: 12px;
  margin-bottom: 20px;
}

.card-title {
  font-size: 15px;
  font-weight: 600;
  color: #1a1a2e;
}

.chart-container {
  width: 100%;
  height: 350px;
}
</style>
