<template>
  <el-row class="main">
    <el-row class="main-content">
      <el-col :span="18" class="content-left">
        <el-row>
          <el-menu mode="horizontal">
            <el-menu-item v-for="item of recommen.tags" :key="item.id">
              <span @click="check(item.articleTag)">{{ item.articleTag }}</span>
            </el-menu-item>
          </el-menu>
        </el-row>

        <el-row :gutter="16" class="card-wrapper">
          <transition-group name="fade">
            <Articler v-for="item in temporary" v-show="temporary" :key="item.id" :article="item"></Articler>
          </transition-group>
        </el-row>

        <!--        <div style="height: 25px"></div>-->

        <el-col class="pagination">
          <el-pagination
            :current-page="pageInfo.pageNum"
            :page-count="pageInfo.pages"
            layout="prev, pager, next"
            @current-change="change"
          ></el-pagination>
        </el-col>
      </el-col>
      <el-col :span="6" class="content-right">
        <ReArticle v-for="item in articles.slice(9, 14)" :key="item.id" :article="item"></ReArticle>
        <!-- <el-row>
          <el-image v-if="recommen.imagUrl" :src="recommen.imagUrl"></el-image>
        </el-row>-->
        <!-- <h3 class="sub-title">#热门标签</h3>
        <el-row class="friends-link">
          <waterfall :col="3" :data="favorites" :gutterWidth="10">
            <div
              class="friends-item"
              v-for="(item, index) in favorites"
              :key="index"
            >
              <el-button>
                {{ item.userName }}
              </el-button>
            </div>
          </waterfall>
        </el-row>-->
      </el-col>
    </el-row>
  </el-row>
</template>
<script>
import {mapState} from "vuex";
import {queryByTags} from "../../api/BlogApi"

export default {
  name: "note",
  data() {
    return {
      favorites: [
        {
          userName: "动漫之家",
          id: 120,
          link: "链接",
        },
      ],
      temporary: [],
      pageInfo: {},
      currentTags: "ES6"
    };
  },
  async created() {
    this.check()
  },
  computed: {
    ...mapState({
      recommen: (state) => state.recommen,
      articles: (state) => state.articlers
    }),

  },

  methods: {
    async change(index) {
      this.pageInfo = await queryByTags(this.currentTags, index, 6);
      this.temporary = null;
      this.temporary = this.pageInfo.list;
    },
    async check(articleTag = "ES6笔记") {
      this.pageInfo = await queryByTags(articleTag, 0, 6)
      this.currentTags = articleTag;
      this.temporary = null;
      this.temporary = this.pageInfo.list
    },
  },
};
</script>

<style lang="scss" scoped>
.fade-enter-active {
  transition: opacity 0.2s;
}

.fade-leave-active {
  display: none;
}

.fade-enter,
.fade-leave-to /* .fade-leave-active below version 2.1.8 */
{
  opacity: 0;
}

.main {
  background: #ffffff;

  .content-left {
    padding: 16px;
    min-height: 80vh;
    position: relative;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
  }

  .el-menu--horizontal > .el-menu-item {
    height: 35px;
    line-height: 35px;

    a {
      text-decoration: none;
    }
  }

  .favorites-link {
    width: 100%;

    .favorites-item {
      padding: 6px;
      margin: 6px;
      border-radius: 6px;
      text-align: center;

      .el-button {
        width: 100%;
      }
    }
  }

  .pagination {
    display: flex;
    justify-content: center;
    position: absolute;
    bottom: 0px;
  }
}
</style>
