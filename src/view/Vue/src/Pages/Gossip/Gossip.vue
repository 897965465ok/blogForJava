<template>
  <el-row class="gossip">
    <el-row>
      <el-col v-for="item in articles" :key="item.id" :span="8" class="card">
        <div class="picture">
          <!-- <img :src="item.picture" /> -->
        </div>
        <h4>{{ item.paragraph }}</h4>
      </el-col>
    </el-row>

    <el-col class="pagination">
      <el-pagination :total="1000" layout="prev, pager, next"></el-pagination>
    </el-col>

  </el-row>
</template>

<script>
export default {
  name: "Gossip",
  data() {
    return {
      articles: [],
    };
  },
  async created() {
    let {data} = await this.$api.get("/v1/gossip");
    this.articles = data["articles"];
  },
};
</script>

<style lang="scss" scoped>
.gossip {
  height: 100vh;
  text-align: center;
}
</style>
