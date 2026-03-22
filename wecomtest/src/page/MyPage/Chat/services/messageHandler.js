/**
 * 消息处理器
 * 
*/

export function handleMessage(msg,{ currentUser, conversations, users,GLOBAL }) {
  console.log(msg)
  switch (msg.type) {
    case "history": {
      const historyList = msg.data
      historyList.forEach(message => {
        const convId = message.conversationId
        let target
        if (convId === GLOBAL) {
          target = GLOBAL
        } else {
          const users = convId.split("_")
          target = users.find(u => u !== currentUser)
        }
        if (!conversations[target]) {
          conversations[target] = []
        }
        conversations[target].push(message)
      })
      break
    }
    case "chat": {
      const message = msg.data
      const convId = message.conversationId
      let target
      if (convId === GLOBAL) {
        target = GLOBAL
      } else {
        // convId 例如 lisi_zhangsan
        const users = convId.split("_")
        // 找出对方
        target = users.find(u => u !== currentUser)
      }
      if (!conversations[target]) {
        conversations[target] = []
      }
      conversations[target].push(message)
      break
    }

    case "join":
      conversations[GLOBAL].push({
        type: "system",
        content: msg.data.user + " 加入了聊天室"
      })
      break

    case "leave":
      conversations[GLOBAL].push({
        type: "system",
        content: msg.data.user + " 离开了聊天室"
      })
      break

    case "users":
      users.value = msg.data
      break
  }
}