let ws = null

export function connect(token, handlers) {

  ws = new WebSocket(`ws://47.104.254.0:30659/chat?token=${token}`)

  console.log("连接成功")
  ws.onopen = () => {
    handlers.onOpen && handlers.onOpen()
  }

  ws.onmessage = (e) => {
    const msg = JSON.parse(e.data)
    handlers.onMessage && handlers.onMessage(msg)
  }

  ws.onclose = () => {
    handlers.onClose && handlers.onClose()
  }
}

export function send(data) {
  if (!ws) return
  console.log(data)
  ws.send(JSON.stringify(data))
}

export function close() {
  ws?.close()
}