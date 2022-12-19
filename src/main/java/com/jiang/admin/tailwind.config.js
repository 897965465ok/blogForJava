/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx,vue}",
  ],
  theme: {
    extend: {},
  },
  plugins: [],
  corePlugins: {
    preflight: false,	// 添加这个配置可以让tailwind不覆盖默认的基础元素的样式，例如html、body、h1等https://tailwindcss.com/docs/preflight
  }
}