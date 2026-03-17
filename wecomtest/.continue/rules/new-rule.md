---
description: 这是我的vue前端架构
---

# Project Architecture
这是vue前端文件分布
├── 📁 .continue
│   ├── 📁 prompts
│   │   └── 📄 new-prompt.md
│   └── 📁 rules
│       └── 📄 new-rule.md
├── 📄 .gitignore
├── 📁 .vscode
│   ├── 📄 extensions.json
│   └── 📄 settings.json
├── 📁 dist
│   ├── 📁 assets
│   │   ├── 📄 index-BKuwiH0A.css
│   │   └── 📄 index-C_UVgDN2.js
│   ├── 📄 favicon.ico
│   └── 📄 index.html
├── 📄 index.html
├── 📄 jsconfig.json
├── 📄 openapitools.json
├── 📄 package-lock.json
├── 📄 package.json
├── 📁 public
│   └── 📄 favicon.ico
├── 📄 README.md
├── 📁 src
│   ├── 📁 api
│   │   ├── 📄 client.ts
│   │   ├── 📄 favoriteApi.js
│   │   ├── 📄 fileApi.js
│   │   ├── 📁 generated
│   │   │   ├── 📄 .gitignore
│   │   │   ├── 📄 .npmignore
│   │   │   ├── 📁 .openapi-generator
│   │   │   │   ├── 📄 FILES
│   │   │   │   └── 📄 VERSION
│   │   │   ├── 📄 .openapi-generator-ignore
│   │   │   ├── 📄 api.ts
│   │   │   ├── 📁 apis
│   │   │   │   ├── 📄 auth-controller-api.ts
│   │   │   │   ├── 📄 favorite-controller-api.ts
│   │   │   │   ├── 📄 json-controller-api.ts
│   │   │   │   └── 📄 we-message-controller-api.ts
│   │   │   ├── 📄 base.ts
│   │   │   ├── 📄 common.ts
│   │   │   ├── 📄 configuration.ts
│   │   │   ├── 📁 docs
│   │   │   │   ├── 📄 AuthControllerApi.md
│   │   │   │   ├── 📄 FavoriteControllerApi.md
│   │   │   │   ├── 📄 FavoriteFolderDto.md
│   │   │   │   ├── 📄 JsonControllerApi.md
│   │   │   │   ├── 📄 LoginRequest.md
│   │   │   │   ├── 📄 LoginResponse.md
│   │   │   │   ├── 📄 MarkDownFileDto.md
│   │   │   │   ├── 📄 OnePathFilesVo.md
│   │   │   │   ├── 📄 OnePathFileVo.md
│   │   │   │   ├── 📄 RegisterRequest.md
│   │   │   │   └── 📄 WeMessageControllerApi.md
│   │   │   ├── 📄 git_push.sh
│   │   │   ├── 📄 index.ts
│   │   │   └── 📁 models
│   │   │       ├── 📄 favorite-folder-dto.ts
│   │   │       ├── 📄 index.ts
│   │   │       ├── 📄 login-request.ts
│   │   │       ├── 📄 login-response.ts
│   │   │       ├── 📄 mark-down-file-dto.ts
│   │   │       ├── 📄 one-path-file-vo.ts
│   │   │       ├── 📄 one-path-files-vo.ts
│   │   │       └── 📄 register-request.ts
│   │   └── 📄 http.ts
│   ├── 📄 App.vue
│   ├── 📁 assets
│   │   ├── 📄 base.css
│   │   ├── 📄 logo.svg
│   │   └── 📄 main.css
│   ├── 📁 auth
│   │   ├── 📄 authStore.js
│   │   └── 📄 token.js
│   ├── 📄 main.js
│   ├── 📁 page
│   │   ├── 📁 MyPage
│   │   │   ├── 📁 Chat
│   │   │   │   ├── 📄 ChatPage.vue
│   │   │   │   ├── 📁 components
│   │   │   │   │   ├── 📄 ChatArea.vue
│   │   │   │   │   ├── 📄 ChatWindow.vue
│   │   │   │   │   └── 📄 UserPanel.vue
│   │   │   │   ├── 📁 services
│   │   │   │   │   ├── 📄 messageHandler.js
│   │   │   │   │   └── 📄 websocket.js
│   │   │   │   ├── 📁 store
│   │   │   │   └── 📁 style
│   │   │   │       └── 📄 chat.css
│   │   │   ├── 📁 EditFile
│   │   │   │   ├── 📄 SidebarFileList.vue
│   │   │   │   └── 📄 Workspace.vue
│   │   │   ├── 📁 Favorite
│   │   │   │   └── 📄 FavoriteButton.vue
│   │   │   ├── 📁 FileComponent
│   │   │   │   ├── 📄 ContentViewer.vue
│   │   │   │   ├── 📄 FileBrowser.vue
│   │   │   │   ├── 📄 FileItem.vue
│   │   │   │   ├── 📄 FileList.vue
│   │   │   │   ├── 📄 fileUtils.js
│   │   │   │   └── 📄 useFileExplorer.js
│   │   │   ├── 📄 Home.vue
│   │   │   ├── 📁 LingMessage
│   │   │   │   └── 📄 Message.vue
│   │   │   ├── 📁 utils
│   │   │   │   └── 📄 fileTreePrinter.js
│   │   │   └── 📁 vditor
│   │   │       ├── 📄 MarkdownEditor.vue
│   │   │       └── 📄 vditor.vue
│   │   ├── 📁 StudyPage
│   │   │   ├── 📄 Child.vue
│   │   │   └── 📄 Parent.vue
│   │   └── 📁 WHUI
│   │       ├── 📄 About.vue
│   │       ├── 📄 Home.vue
│   │       ├── 📄 MyPage.vue
│   │       ├── 📄 Patner.vue
│   │       ├── 📄 PatnerHistore.vue
│   │       └── 📄 Register.vue
│   ├── 📁 router
│   │   └── 📄 index.js
│   └── 📁 views
│       ├── 📄 Login.vue
│       ├── 📄 PrivateChat.vue
│       └── 📄 Register.vue
└── 📄 vite.config.js


# 文件基本解释和用途
## src/api文件
其中 api 文件夹是利用 openapi 根据我的后端接口生成的api文件

## src/page
这里面是我写的一写页面
其中包括StudyPage,StudyPage 和MyPage

## src/router
这里面是我利用vue-router做的路由
