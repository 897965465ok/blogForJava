<template>
  <div class="login-container">
    <!-- 背景装饰 -->
    <div class="bg-decoration">
      <div class="circle c1"></div>
      <div class="circle c2"></div>
      <div class="circle c3"></div>
      <div class="circle c4"></div>
    </div>

    <div class="login-card">
      <div class="card-header">
        <div class="logo-area">
          <span class="logo-icon">📝</span>
        </div>
        <h3 class="login-title">欢迎回来</h3>
        <p class="login-subtitle">请登录您的账号</p>
      </div>

      <el-form
          ref="loginForm"
          :model="form"
          :rules="rules"
          class="login-form"
      >
        <el-form-item prop="username">
          <el-input
              v-model="form.username"
              placeholder="请输入账号"
              :prefix-icon="User"
              size="large"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="form.password"
              placeholder="请输入密码"
              type="password"
              :prefix-icon="Lock"
              size="large"
              show-password
              @keyup.enter="onSubmit('loginForm')"
          />
        </el-form-item>
        <el-form-item>
          <el-button
              class="login-btn"
              type="primary"
              size="large"
              @click="onSubmit('loginForm')"
              :loading="loading"
          >
            登 录
          </el-button>
        </el-form-item>
      </el-form>

      <div class="card-footer">
        <span>还没有账号？<a href="#">联系管理员</a></span>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import {User, Lock} from '@element-plus/icons-vue'
import * as blogApi from '@/api/BlogApi'
import {useRouter} from 'vue-router';
import Cookies from "js-cookie";
import {useStore} from "@/stores/index";

const use_Store = useStore();
const router = useRouter();
const loading = ref(false)
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
      loading.value = true
      const {username: account, password, email} = form.value;
      let {data} = await blogApi.userLogin(account, password, email)
      if (data.code == 200) {
        let expires = new Date(new Date() * 1 + data.result.tokenTimeout * 1000);
        Cookies.set('token', data.result.tokenValue, {expires: expires})
        await use_Store.setUserInfo(data.result);
        await router.push("/menu")
      } else {
        loading.value = false
      }
    } else {
      return false;
    }
  });
};
</script>

<style lang="scss" scoped>
@import '@/assets/icon/iconfont.css';

.login-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100vw;
  height: 100vh;
  background: linear-gradient(135deg, #0f0c29 0%, #302b63 50%, #24243e 100%);
  overflow: hidden;
}

/* 背景装饰粒子 */
.bg-decoration {
  position: absolute;
  inset: 0;
  pointer-events: none;
}

.circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.12;
  filter: blur(60px);
}

.c1 {
  width: 500px;
  height: 500px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  top: -200px;
  right: -150px;
  animation: float1 10s ease-in-out infinite;
}

.c2 {
  width: 400px;
  height: 400px;
  background: linear-gradient(135deg, #f093fb, #f5576c);
  bottom: -150px;
  left: -100px;
  animation: float2 12s ease-in-out infinite alternate;
}

.c3 {
  width: 250px;
  height: 250px;
  background: linear-gradient(135deg, #4facfe, #00f2fe);
  top: 30%;
  left: 5%;
  animation: float3 8s ease-in-out infinite 2s;
}

.c4 {
  width: 180px;
  height: 180px;
  background: linear-gradient(135deg, #43e97b, #38f9d7);
  bottom: 10%;
  right: 8%;
  animation: float1 15s ease-in-out infinite 3s;
}

@keyframes float1 {
  0%, 100% { transform: translate(0, 0) scale(1); }
  33% { transform: translate(40px, -40px) scale(1.1); }
  66% { transform: translate(-20px, 20px) scale(0.9); }
}

@keyframes float2 {
  0%, 100% { transform: translate(0, 0) scale(1); }
  50% { transform: translate(-30px, 30px) scale(1.15); }
}

@keyframes float3 {
  0%, 100% { transform: translate(0, 0); }
  50% { transform: translate(20px, -20px); }
}

/* 登录卡片 */
.login-card {
  position: relative;
  z-index: 1;
  width: 420px;
  padding: 44px 40px 36px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border-radius: 20px;
  box-shadow:
    0 25px 80px rgba(0, 0, 0, 0.3),
    0 0 0 1px rgba(255, 255, 255, 0.08);
  animation: slideUp 0.6s cubic-bezier(0.16, 1, 0.3, 1);
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(40px) scale(0.96);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.card-header {
  text-align: center;
  margin-bottom: 32px;
}

.logo-area {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 68px;
  height: 68px;
  margin-bottom: 18px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  border-radius: 18px;
  box-shadow: 0 8px 28px rgba(102, 126, 234, 0.35);
}

.logo-icon {
  font-size: 30px;
  line-height: 1;
}

.login-title {
  margin: 0 0 6px;
  font-size: 26px;
  font-weight: 700;
  color: #1a1a2e;
  letter-spacing: -0.5px;
}

.login-subtitle {
  margin: 0;
  font-size: 14px;
  color: #999;
}

/* 表单 */
.login-form {
  width: 100%;
}

.login-form :deep(.el-input__wrapper) {
  background: #f5f7fa;
  border: none;
  box-shadow: none !important;
  border-radius: 12px;
  padding: 4px 16px;
  transition: background 0.3s, box-shadow 0.3s;
}

.login-form :deep(.el-input__wrapper:hover),
.login-form :deep(.el-input__wrapper.is-focus) {
  background: #fff;
  box-shadow: 0 0 0 1.5px #667eea !important;
}

.login-form :deep(.el-input__inner) {
  height: 48px;
  font-size: 14px;
}

.login-form :deep(.el-input__prefix) {
  margin-right: 10px;
}

.login-form :deep(.el-input__prefix-inner) svg {
  color: #999;
  width: 18px;
  height: 18px;
}

.login-form :deep(.el-form-item) {
  margin-bottom: 24px;
}

.login-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 6px;
  border: none;
  border-radius: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  transition: transform 0.2s, box-shadow 0.2s;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 28px rgba(102, 126, 234, 0.45);
}

.login-btn:active {
  transform: translateY(0);
}

.card-footer {
  margin-top: 28px;
  text-align: center;
  font-size: 13px;
  color: #999;
}

.card-footer a {
  color: #667eea;
  text-decoration: none;
  font-weight: 500;
}

.card-footer a:hover {
  text-decoration: underline;
}
</style>
