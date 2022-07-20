package cn.windis.pluginnonebot.webSocket

import cn.windis.pluginnonebot.utils.MessageBuilder
import cn.windis.pluginnonebot.utils.Serializer
import cn.windis.pluginnonebot.webSocket.client.Client
import io.ktor.websocket.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking

interface IWsConnection {
    var session: WebSocketSession?
    var isSessionValid: Boolean
    var selfId: String
    var messageChannel: Channel<String>

    fun disconnect(closeReason: CloseReason) {
        runBlocking { Client.session?.close(closeReason) }
        Client.session = null
        Client.isSessionValid = false
    }

    fun broadcast(message: String) {
        runBlocking {
            messageChannel.send(message)
        }
    }

    fun broadcast(message: Map<String, Any>) {
        runBlocking { messageChannel.send(Serializer.serialize(message)) }
    }

    fun broadcast(message: MessageBuilder) {
        runBlocking { messageChannel.send(Serializer.serialize(message.build())) }
    }
}