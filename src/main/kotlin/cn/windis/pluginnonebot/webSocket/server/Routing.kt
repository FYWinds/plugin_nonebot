@file:Suppress("UNCHECKED_CAST")

package cn.windis.pluginnonebot.webSocket.server

import cn.windis.pluginnonebot.PluginNonebot
import cn.windis.pluginnonebot.utils.Serializer
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

fun Application.configureRouting() {
    routing {
        webSocket("/ws") {
            Socket.connect(this)
            for (frame in incoming) {
                when (frame) {
                    // 由于必须指定固定dataclass的缘故没使用 ktor 的 序列化和反序列化
                    is Frame.Text -> {
                        val msg: Map<Any, Any> =
                            Serializer.deserialize(frame.readText(), Map::class.java) as Map<Any, Any>
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
                                val params: Map<Any, Any> = data["params"] as Map<Any, Any>
                            }
                            "Disconnect" -> {
                                val errorCode = data["errorCode"] as Int
                                val errorMessage = data["errorMessage"] as String
                            }
                            else -> {
                                // TODO 其他操作
                            }
                        }
                    }
                    else -> {
                        PluginNonebot.logger.warning("Unsupported frame type: ${frame.frameType}")
                        PluginNonebot.logger.warning("Closing the connection")
                        Socket.disconnect(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "Unsupported frame type"))
                    }
                }
            }
        }
    }
}