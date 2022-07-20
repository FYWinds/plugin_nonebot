@file:Suppress("UNCHECKED_CAST")

package cn.windis.pluginnonebot.webSocket.server

import cn.windis.pluginnonebot.PluginNonebot
import cn.windis.pluginnonebot.utils.Serializer
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

fun Application.configureRouting() {
    routing {
        webSocket("/ws") {
            Socket.connect(this)
            launch { incomingMessages() }
            outgoingMessages()
        }
    }
}

private suspend fun DefaultWebSocketServerSession.incomingMessages() {
    try {
        for (message in incoming) {
            message as? Frame.Text ?: continue
            val msg: Map<Any, Any> = Serializer.deserialize(message.readText(), Map::class.java) as Map<Any, Any>
            val action: String = msg["action"] as String
            val data: Map<Any, Any> = msg["data"] as Map<Any, Any>

            when (action) {
                "Authorization" -> {
                    val token = data["token"] as String
                    if (token == PluginNonebot.getToken()) {
                        Socket.setValid()
                    } else {
                        PluginNonebot.logger.warning("Authorization failed")
                        PluginNonebot.logger.warning("Closing the connection")
                        Socket.disconnect(
                            CloseReason(
                                CloseReason.Codes.CANNOT_ACCEPT,
                                "Authorization failed"
                            )
                        )
                    }
                }
                "Call_API" -> {
                    val api: String = data["api"] as String
                    val params: MutableMap<String, Any> = data["params"] as MutableMap<String, Any>
                    val seq: String = data["seq"] as String
                    Socket.call(api, params, seq)
                }
                "Disconnect" -> {
                    val errorCode = data["errorCode"] as Int
                    val errorMessage = data["errorMessage"] as String
                }
                else -> {
                    PluginNonebot.logger.warning("未知消息类型: ${Serializer.serialize(msg)}")
                }
            }
        }
    } catch (e: CancellationException) {
        PluginNonebot.logger.warning("接收消息线程被中断")
        PluginNonebot.instance.reload()
    } catch (e: Exception) {
        e.printStackTrace()
        PluginNonebot.logger.warning("处理消息失败: ${e.javaClass} ${e.localizedMessage}")
    }
}

private suspend fun DefaultWebSocketServerSession.outgoingMessages() {
    while (true) {
        try {
            send(Socket.messageChannel.receive())
        } catch (e: CancellationException) {
            delay(1000)
            println("消息发送线程被中断: ${e.localizedMessage}")
            PluginNonebot.instance.reload()
        } catch (e: Exception) {
            println("发送消息失败: ${e.localizedMessage}")
        }
    }
}