package cn.windis.pluginnonebot.webSocket.server

import io.ktor.websocket.*

class Socket {
    companion object {
        private var session: WebSocketSession? = null
        private var isSessionValid: Boolean = false

        fun connect(session: WebSocketSession) {
            Companion.session = session
        }

        suspend fun broadcast(message: String) {
            session?.send(message)
        }

        suspend fun disconnect(reason: CloseReason) {
            session?.close(reason)
            session = null
            isSessionValid = false
        }

        fun setValid() {
            isSessionValid = true
        }
    }
}