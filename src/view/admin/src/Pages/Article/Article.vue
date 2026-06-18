<script lang='ts' setup>
import {onBeforeMount, onMounted, reactive, ref, toRaw, inject} from 'vue';
import {storeToRefs} from 'pinia';
import {useStore} from '@/stores/article';
import {useRoute, useRouter} from 'vue-router';
import TableVue from './component/ArticleTable.vue';
import {tagStore as useTagStore} from "@/stores/tag";
import * as BlogApi from "@/api/BlogApi"
import {ElMessage} from "element-plus";
import type {UploadUserFile} from "element-plus/lib/components";
import Markdown from "@/Pages/Article/component/Markdown.vue";
import {useAuth} from "@/utils/useAuth";


/**
 * 仓库
 */
const store = useStore();
const {treeMap} = storeToRefs(store);
const tagStore = useTagStore();
const {queryManyTag} = tagStore
const treeTwo = ref();
const ArticleList = ref([]);
const select = ref<Array<string>>([]);
const sign = ref<Number>(0);
const tag = ref();
const sideArticle = ref();
const hot = ref();
const fileRaw = ref();
const openCreateBox = ref(false)
const openMarkdownBox = ref<Boolean>(false);
const openDeleteBox = ref<boolean>(false)
const changeId = ref<Array<number>>();
const MarkdownRef = ref();
/**
 * 路由对象
 */
const route = useRoute();
/**
 * 路由实例
 */
const router = useRouter();
//console.log('1-开始创建组件-setup')
/**
 * 数据部分
 */

//console.log('2.组件挂载页面之前执行----onBeforeMount')

onMounted(async () => {

  let {result}: any = await queryManyTag(1, 7);
  select.value.push(...result);

})

// 使用toRefs解构
// let { } = { ...toRefs(data) }


function openBox(number: Number) {
  switch (number) {
    case 1: {
      openCreateBox.value = true;
      break
    }
    case 2: {
      openMarkdownBox.value = true
      break
    }
  }

  sign.value = number;
}


function checkButton(selectorList: any) {
  ArticleList.value = selectorList
}


async function deleteSelectorArticleList() {
  let id = ArticleList.value.map((item: any) => item.id)
  // 删除选择中的文章
  let {code, message} = await BlogApi.deleteArticle(id)

  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: `删除成功`,
      type: "success",
    });
    // 刷新文章表格
    window.dispatchEvent(new CustomEvent('article-refresh'))
  } else {
    ElMessage.error({
      message: "删除失败",
      type: "error",
    });
  }


  openDeleteBox.value = !openDeleteBox.value

}

async function changeArticle() {
  let {code, message}: any = await MarkdownRef.value.changeArticle();
  console.log(code, message)

  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: `修改成功`,
      type: "success",
    });
  } else {
    ElMessage.error({
      message: "修改失败",
      type: "error",
    });
  }

  // 修改选中的文章
  openMarkdownBox.value = false
}


async function creatArticle() {
  // 创建文章
  openCreateBox.value = true
  const form: FormData = new FormData();
  form.append("tag", tag.value);
  form.append("sideArticle", sideArticle.value);
  form.append("hot", hot.value);
  form.append("file", fileRaw.value);
  let {code, message}: any = await BlogApi.createArticle(form);
  if (code == 200 && message == "SUCCESS") {
    ElMessage.success({
      message: "创建文章成功",
      type: "success",
    });
  } else {
    ElMessage.error({
      message: "创建文章失败",
      type: "error",
    });
  }
  openCreateBox.value = false;
}

// default-expanded-keys
// default-checked-keys


const keys: any[] = [];

const strictly = ref(false)

function GeneratorKeys(treeData: Array<Object>) {
  treeData.forEach((item: any) => {
    keys.push(item.menuId)
    if (Array.isArray(item.children)) {
      GeneratorKeys(item.children)
    }
  })

}

//点击展开
const allOpen = (isCheck: boolean) => {
  let RawArr: any = toRaw(reactive(treeMap.value))
  GeneratorKeys(RawArr)
  if (isCheck) {
    keys.forEach(id => {
      let node = treeTwo.value.getNode(id)
      treeTwo.value.expandNode(node)
    })
  } else {
    keys.forEach(id => {
      let node = treeTwo.value.getNode(id)
      treeTwo.value.collapseNode(node)
    })
  }

}
// 全选/反选
const allSelector = (isCheck: boolean) => {
  let RawArr: any = toRaw(reactive(treeMap.value))
  GeneratorKeys(RawArr)
  if (isCheck) {
    keys.forEach(id => {
      treeTwo.value.setChecked(id, true)
    })
  } else {
    keys.forEach(id => {
      treeTwo.value.setChecked(id, false)
    })
  }

}
// 父子联动
const linkage = (isCheck: boolean) => {

  strictly.value = !strictly.value

}

const changeFile = (file: UploadUserFile) => {
  fileRaw.value = file.raw
}

const FileRemove = () => {

  fileRaw.value = null;
}

// ── 批量导入 ──
const openBatchBox = ref(false)
const batchFiles = ref<{ name: string; tag: string; raw: File; status: string; progress: number }[]>([])
const batchLoading = ref(false)
const batchProgress = ref(0)
const isDragOver = ref(false)

// 递归遍历文件夹
function traverseFileTree(entry: any, path = ''): Promise<{ name: string; file: File }[]> {
  return new Promise((resolve) => {
    if (entry.isFile) {
      entry.file((file: File) => {
        if (file.name.endsWith('.md')) {
          const fullPath = path ? `${path}/${file.name}` : file.name
          resolve([{ name: fullPath, file }])
        } else { resolve([]) }
      }, () => resolve([]))
    } else if (entry.isDirectory) {
      const dirReader = entry.createReader()
      dirReader.readEntries(async (entries: any[]) => {
        const results = await Promise.all(entries.map((subEntry: any) =>
          traverseFileTree(subEntry, path ? `${path}/${entry.name}` : entry.name)
        ))
        resolve(results.flat())
      }, () => resolve([]))
    } else { resolve([]) }
  })
}

async function handleDrop(e: DragEvent) {
  isDragOver.value = false
  const items = e.dataTransfer?.items
  if (!items || items.length === 0) return
  const allMdFiles: typeof batchFiles.value = [...batchFiles.value]
  const seen = new Set(batchFiles.value.map(f => f.name))
  for (let i = 0; i < items.length; i++) {
    const item = items[i]
    const entry = item.webkitGetAsEntry?.()
    if (entry) {
      const files = await traverseFileTree(entry)
      for (const { name: fullPath, file } of files) {
        if (seen.has(fullPath)) continue
        seen.add(fullPath)
        let tag = ''
        if (fullPath.includes('/')) tag = fullPath.split('/')[0]
        allMdFiles.push({ name: fullPath, tag, raw: file, status: 'waiting', progress: 0 })
      }
    } else {
      const file = item.getAsFile()
      if (file && file.name.endsWith('.md') && !seen.has(file.name)) {
        seen.add(file.name)
        allMdFiles.push({ name: file.name, tag: '', raw: file, status: 'waiting', progress: 0 })
      }
    }
  }
  batchFiles.value = allMdFiles
}

// 选择文件夹
function selectFolder() {
  const input = document.createElement('input')
  input.type = 'file'
  // @ts-ignore
  input.webkitdirectory = true
  input.multiple = true
  input.accept = '.md'
  input.onchange = () => {
    const files: typeof batchFiles.value = [...batchFiles.value]
    const seen = new Set(batchFiles.value.map(f => f.name))
    for (const file of Array.from(input.files || [])) {
      if (!file.name.endsWith('.md')) continue
      // @ts-ignore
      const relPath = file.webkitRelativePath || file.name
      if (seen.has(relPath)) continue
      seen.add(relPath)
      let tag = ''
      if (relPath.includes('/')) tag = relPath.split('/')[0]
      files.push({ name: relPath, tag, raw: file, status: 'waiting', progress: 0 })
    }
    batchFiles.value = files
  }
  input.click()
}

function clearBatchFiles() { batchFiles.value = [] }

async function submitBatchImport() {
  if (batchFiles.value.length === 0) return
  batchLoading.value = true
  batchProgress.value = 0
  let successCount = 0, failCount = 0
  const total = batchFiles.value.length
  for (let i = 0; i < total; i++) {
    const item = batchFiles.value[i]
    item.status = 'uploading'
    item.progress = 0
    try {
      let {code, message} = await BlogApi.uploadSingleArticle(item.tag, item.raw, (pct) => { item.progress = pct })
      if (code == 200 && message == "SUCCESS") { item.status = 'success'; item.progress = 100; successCount++ }
      else { item.status = 'failed'; failCount++ }
    } catch { item.status = 'failed'; failCount++ }
    batchProgress.value = Math.round(((i + 1) / total) * 100)
  }
  batchLoading.value = false
  if (failCount === 0) {
    ElMessage.success(`全部导入成功，共 ${successCount} 篇文章`)
    window.dispatchEvent(new CustomEvent('article-refresh'))
    openBatchBox.value = false
    batchFiles.value = []
  } else {
    ElMessage.warning(`导入完成：成功 ${successCount} 篇，失败 ${failCount} 篇`)
  }
  batchProgress.value = 0
}

async function invoke() {
  switch (sign.value) {
    case 1: {
      await creatArticle();
      break
    }
    case 2 : {
      await changeArticle();
      break
    }
  }

}


const getMarkdown = () => {
  let id: any = ArticleList.value.map((item: any) => item.id)
  MarkdownRef.value.getOneMarkdown(id);
}

const props = {
  value: 'menuId', //   表头 id
  label: 'menuName', // 表头 内容
  children: 'children', // 表头 子节点
}


</script>

<template>

  <el-row class=" mt-4 mb-4 ">
    <el-button v-if="useAuth('article:plus')" @click="openBox(1)">新增</el-button>
    <el-button v-if="useAuth('article:edit')" :disabled="(ArticleList.length != 1)" @click="openBox(2)">修改</el-button>
    <el-button v-if="useAuth('article:delete') " :disabled="(ArticleList.length < 1)" @click="openDeleteBox = !openDeleteBox">删除</el-button>
    <el-button v-if="useAuth('article:plus')" @click="openBatchBox = true">批量导入</el-button>
  </el-row>

  <TableVue @check="checkButton"></TableVue>

  <el-dialog v-model="openCreateBox" destroy-on-close title="文章上传" width="35%">
    <template #default>
      <el-form :model="form">
        <div class="form-header">
          <el-row class="header-upload">
            <el-upload
                @change="changeFile"
                @remove="FileRemove"
                ref="uploadRef"
                :limit="1"
                style="width: 100%;"
                drag
                action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                multiple
                :auto-upload="false"
            >
              <el-icon class="el-icon--upload">
                <upload-filled/>
              </el-icon>
              <div class="el-upload__text">
                <em>拖入文件 点击上传</em>
              </div>
            </el-upload>

          </el-row>
        </div>

        <el-row class="form-body">
          <el-form-item label="热门">
            <el-switch v-model="hot"/>
          </el-form-item>
          <el-form-item label="侧边栏文章">
            <el-switch v-model="sideArticle"/>
          </el-form-item>
          <el-form-item label="分类">
            <el-select
                v-model="tag"
                placeholder="ES6"
                style="width: 70%"
            >
              <el-option
                  v-for="(item,index) in select"
                  :key="item.id"
                  :label="item.articleTag"
                  :value="item.articleTag"
              />
            </el-select>
          </el-form-item>
        </el-row>

        <el-row class="form-footer">
          <el-form-item>
            <el-button :disabled=" fileRaw == null ? true:false" type="primary" @click="invoke">确定</el-button>
            <el-button @click="openCreateBox = !openCreateBox">取消</el-button>
          </el-form-item>
        </el-row>

      </el-form>
    </template>


  </el-dialog>

  <!-- 删除遮罩层 -->
  <el-dialog v-model="openDeleteBox" title="确认信息" width="30%" align-center>
    <span>是否删除这些文章?</span>
    <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="deleteSelectorArticleList"> 确定 </el-button>
            <el-button @click="openDeleteBox = false">取消</el-button>
          </span>
    </template>
  </el-dialog>

  <el-dialog v-model="openMarkdownBox" title="修改文章" :fullscreen="true" @opened="getMarkdown">
    <Markdown ref="MarkdownRef"></Markdown>
    <template #footer class=" top-10 ">
      <el-button type="primary" @click="invoke">保存</el-button>
      <el-button @click="openMarkdownBox = !openMarkdownBox">取消</el-button>
    </template>
  </el-dialog>

  <!-- 批量导入对话框 -->
  <el-dialog v-model="openBatchBox" title="批量导入 Markdown 文章" width="50%" destroy-on-close @closed="clearBatchFiles">

    <div class="drop-zone" :class="{ 'drop-zone--active': isDragOver }"
        @click="selectFolder"
        @dragenter.prevent="isDragOver = true"
        @dragover.prevent="isDragOver = true"
        @dragleave.prevent="isDragOver = false"
        @drop.prevent="handleDrop">
      <el-icon class="el-icon--upload" :size="40"><upload-filled/></el-icon>
      <p class="drop-zone__text">{{ isDragOver ? '释放以导入' : '点击选择文件夹，或拖拽 .md 文件/文件夹到这里' }}</p>
    </div>

    <div v-if="batchFiles.length > 0" style="margin-top:16px;">
      <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:8px;">
        <h4 style="margin:0;">待导入文件（{{ batchFiles.length }} 个）</h4>
        <el-button size="small" text @click="clearBatchFiles" :disabled="batchLoading">清空列表</el-button>
      </div>
      <el-table :data="batchFiles" max-height="350" size="small" border>
        <el-table-column prop="name" label="文件路径" min-width="180"/>
        <el-table-column prop="tag" label="分类标签" width="100"/>
        <el-table-column label="状态" width="180">
          <template #default="{ row }">
            <div v-if="row.status === 'waiting'" style="color:#999;">等待上传</div>
            <div v-else-if="row.status === 'uploading'" style="display:flex;align-items:center;gap:8px;">
              <el-progress :percentage="row.progress" :stroke-width="12" :width="120" />
            </div>
            <div v-else-if="row.status === 'success'" style="color:#67c23a;">✅ 成功</div>
            <div v-else-if="row.status === 'failed'" style="color:#f56c6c;">❌ 失败</div>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <template #footer>
      <div v-if="batchLoading" style="margin-bottom:12px;">
        <el-progress :percentage="batchProgress" :stroke-width="16" :text-inside="true"
          :status="batchProgress >= 100 ? 'success' : undefined" />
        <p style="font-size:12px;color:#999;margin-top:4px;text-align:center;">
          正在上传第 {{ batchFiles.filter(f => f.status === 'uploading' || f.status === 'success').length }} / {{ batchFiles.length }} 篇...
        </p>
      </div>
      <el-button type="primary" :loading="batchLoading" :disabled="batchFiles.length === 0 || batchLoading" @click="submitBatchImport">
        开始导入（{{ batchFiles.length }} 篇）
      </el-button>
      <el-button @click="openBatchBox = false" :disabled="batchLoading">取消</el-button>
    </template>
  </el-dialog>


</template>

<style lang='scss' scoped>
.form-header {
  .header-upload {
    .el-upload-list {
      margin: 0;
    }
  }

}

:deep(.el-upload-list) {
  margin: 0;
}

.form-body {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
}

/* ── 批量导入拖拽区域 ── */
.drop-zone {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 140px;
  border: 2px dashed #d9d9d9;
  border-radius: 8px;
  background: #fafafa;
  cursor: pointer;
  transition: border-color 0.3s, background 0.3s;
}
.drop-zone:hover { border-color: #667eea; background: #f0f2ff; }
.drop-zone--active { border-color: #667eea; background: #e8ecff; }
.drop-zone__text { margin: 8px 0 0; font-size: 14px; color: #999; }
.drop-zone--active .drop-zone__text { color: #667eea; }
</style>

