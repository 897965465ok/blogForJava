# 📝 Mblog - 个人博客系统

基于 **Spring Boot 2.7 + Vue 3 + MyBatis-Plus** 构建的个人博客系统，支持文章管理、标签系统、用户权限管理、MinIO 文件存储等功能。

## 🚀 技术栈

| 层级 | 技术 |
|------|------|
| **后端框架** | Spring Boot 2.7.6 |
| **ORM** | MyBatis-Plus 3.3.1 |
| **数据库** | MySQL + Redis |
| **权限认证** | Sa-Token 1.37（JWT 模式） |
| **文件存储** | MinIO |
| **前端** | Vue 3 + Vite + Element Plus |
| **接口文档** | Swagger (Springfox 3.0) |
| **分页** | PageHelper |
| **工具库** | Hutool + FastJSON + Lombok |

## 📦 快速启动

### 前置依赖

- JDK 1.8+
- MySQL
- Redis
- MinIO（可选，用于文件存储）

### 后端启动

```bash
# 修改 application.yaml 中的数据库/Redis/MinIO 配置后运行
mvn spring-boot:run
```

服务默认启动在 `http://localhost:8888`

### 前端启动

```bash
cd src/view/admin
npm install
npm run dev
```

## 📁 项目结构

```
src/main/java/com/jiang/blog/
├── common/          # 公共类（常量、统一响应）
├── config/          # 配置（Redis、Sa-Token、Jackson、Swagger）
├── controller/      # 控制器（Article、User、Menu、Role、Tags、Picture）
├── exception/       # 全局异常处理
├── filter/          # AOP 日志切面
├── model/
│   ├── dao/         # MyBatis Mapper
│   ├── pojo/        # 实体类
│   └── VO/          # 视图对象
├── service/
│   ├── impl/        # 服务实现
│   └── *.java       # 服务接口
└── utils/           # 工具类（MinIO、Redis、加密）
```

## 🛠️ API 接口

所有接口统一前缀 `/v1`，支持 Swagger 文档：

```
http://localhost:8888/swagger-ui/
```

### 主要接口

| 模块 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 文章 | GET | `/v1/queryManyArticle` | 分页查询文章 |
| 文章 | POST | `/v1/addOneArticle` | 新增文章 |
| 文章 | DELETE | `/v1/deleteArticle` | 删除文章 |
| 用户 | POST | `/v1/login` | 用户登录 |
| 用户 | POST | `/v1/register` | 用户注册 |
| 标签 | GET | `/v1/tags` | 查询所有标签 |
| 菜单 | GET | `/v1/getRouter` | 获取前端路由 |
| 菜单 | POST | `/v1/createMenu` | 创建菜单 |
| 角色 | POST | `/v1/createRole` | 创建角色 |
| 图片 | GET | `/v1/wallhaven` | 查询图片 |

## 🔐 权限说明

- 用户 ID `1` 为超级管理员，拥有全部权限
- 权限粒度到按钮级别，通过 Sa-Token 拦截器校验
- 支持角色-菜单权限关联

## 📄 License

MIT
