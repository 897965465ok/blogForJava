<template>
  <el-row class="header">
    <el-col :span="2">
      <div class="logo">
        <router-link to="/">
          <img alt="logo" src="../../assets/logo.png"/>
        </router-link>
      </div>
    </el-col>
    <el-col :offset="12" :span="10">
      <el-menu mode="horizontal">
        <el-menu-item v-for="(item,index) in Menu" :key="index">
          <router-link :to="item.link" tag="a"> {{ item.title }}</router-link>
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
</template>

<script>
export default {
  data: () => ({
    Menu: [
      {
        title: "首页",
        link: "/",

      },
      {
        title: "笔记",
        link: "/note",

      },
      // {
      //   title: "登陆",
      //   link: "/login",
      //
      // },
    ],
  }),
};
</script>

<style lang="scss" scoped>
.header {
  background: #ffffff;

  a {
    text-decoration: none;
    font-size: 16px;

    &:hover {
      color: #00a8ff;
    }
  }

  .logo {
    height: 60px;

    img {
      height: 100%;
    }
  }

  .el-menu--horizontal {
    border: none;
    float: right;
  }
}
</style>
