package cn.windis.pluginnonebot.webSocket.server

import cn.windis.pluginnonebot.PluginNonebot
import cn.windis.pluginnonebot.webSocket.IWsConnection
import io.ktor.websocket.*
import kotlinx.coroutines.channels.Channel

class Socket {
    companion object : IWsConnection {
        override var session: WebSocketSession? = null
        override var isSessionValid: Boolean = false
        override var selfId: String = PluginNonebot.pluginConfig.config.wsConnection!!.name
        override var messageChannel: Channel<String> = Channel()

        fun connect(session: WebSocketSession) {
            Companion.session = session
        }

        fun setValid() {
            isSessionValid = true
        }
    }
}