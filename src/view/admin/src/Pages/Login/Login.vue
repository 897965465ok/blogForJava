<template>
  <div class="flex w-screen h-screen bg-white justify-center content-center">
    <el-form
        ref="loginForm"
        :model="form"
        :rules="rules"
        class="bg-white w-3/12 h-3/12"
    >
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="账号" prop="username">
        <el-input v-model="form.username" placeholder="请输入账号" type="text"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
            v-model="form.password"
            placeholder="请输入密码"
            type="password"
        />
      </el-form-item>
      <el-form-item class="flex justify-between">
        <el-button @click="onSubmit('loginForm')">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import qs from 'qs';
import * as blogApi from '@/api/BlogApi'
import {useRouter} from 'vue-router';
import Cookies from "js-cookie";
import {useStore} from "@/stores/index";

const use_Store = useStore();
const router = useRouter();
const form = ref({
  username: '',
  password: '',
  email: '',
  childWindow: '',
});

const rules = ref({
  username: [
    {required: true, message: '账号不可为空', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '密码不可为空', trigger: 'blur'},
  ],
});

const loginForm = ref(null);

const onSubmit = (formName) => {
  loginForm.value.validate(async (valid) => {
    if (valid) {
      const {username: account, password, email} = form.value;
      let {data} = await blogApi.userLogin(account, password, email)
      if (data.code == 200) {
        let expires = new Date(new Date() * 1 + data.result.tokenTimeout * 1000);
        Cookies.set('token', data.result.tokenValue, {expires: expires})
        await use_Store.setUserInfo(data.result);
        localStorage.setItem("permissions", data.result.permissions)
        localStorage.setItem("roles",data.result.roles)
        await router.push("/menu")
      }

    } else {

      return false;
    }
  });
};
</script>

<style lang="scss" scoped>
@import '@/assets/icon/iconfont.css';

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}
</style>