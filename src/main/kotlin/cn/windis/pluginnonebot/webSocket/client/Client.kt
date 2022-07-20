@file:Suppress("UNCHECKED_CAST")

package cn.windis.pluginnonebot.webSocket.client

import cn.windis.pluginnonebot.PluginNonebot
import cn.windis.pluginnonebot.utils.Serializer
import cn.windis.pluginnonebot.webSocket.IWsConnection
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.ConnectException
import java.util.concurrent.CancellationException

class Client(server: String, retryInterval: Int, maxRetries: Int) {
    private val client = HttpClient() {
        install(WebSockets) {
            pingInterval = 1000
        }
    }

    private var sThread: Thread = Thread {}


    init {
        sThread = Thread {
            try {
                runBlocking {
                    when (maxRetries) {
                        -1 -> {
                            while (true) {
                                try {
                                    connect(server)
                                    break
                                } catch (e: ConnectException) {
                                    PluginNonebot.logger.warning("连接失败，正在重试...")
                                    delay(retryInterval.toLong())
                                }
                            }
                        }
                        0 -> {
                            try {
                                connect(server)
                            } catch (e: ConnectException) {
                                PluginNonebot.logger.warning("连接失败，根据设置停止重连")
                                PluginNonebot.instance.disconnect()
                            }
                        }
                        in 1 until 10 -> {
                            var retries = 0
                            while (true) {
                                try {
                                    connect(server)
                                    break
                                } catch (e: ConnectException) {
                                    PluginNonebot.logger.warning("连接失败，正在重试... ${retries + 1}/$maxRetries")
                                    delay(retryInterval.toLong())
                                    retries++
                                    if (retries >= maxRetries) {
                                        PluginNonebot.logger.warning("连接失败，根据设置停止重连")
                                        PluginNonebot.instance.disconnect()
                                    }
                                }
                            }
                        }
                        else -> {
                            PluginNonebot.logger.warning("重连次数过高或为负数，自动设置为无限重连模式")
                            while (true) {
                                try {
                                    connect(server)
                                    break
                                } catch (e: ConnectException) {
                                    PluginNonebot.logger.warning("连接失败，正在重试...")
                                    delay(retryInterval.toLong())
                                }
                            }
                        }
                    }
                }
            } catch (e: InterruptedException) {
                PluginNonebot.logger.warning("连接线程被中断")
            }
        }
        sThread.start()
    }

    fun stop() {
        try {
            runBlocking { session?.close(CloseReason(CloseReason.Codes.NORMAL, "关闭连接")) }
            client.close()
            sThread.interrupt()
        } catch (_: Exception) {
        }
    }

    private suspend fun connect(server: String) {
        client.webSocket(server) {
            session = this
            launch { incomingMessages() }

            launch { messageChannel.send("{\"action\":\"auth\",\"data\":{\"token\":\"${PluginNonebot.getToken()}\"}}") }

            outgoingMessages()

        }
    }


    private suspend fun DefaultClientWebSocketSession.incomingMessages() {
        try {
            for (message in incoming) {
                message as? Frame.Text ?: continue
                val msg: MutableMap<Any, Any> =
                    Serializer.deserialize(message.readText(), Map::class.java) as MutableMap<Any, Any>
                val action: String = msg["action"] as String
                val data: MutableMap<Any, Any> = msg["data"] as MutableMap<Any, Any>

                when (action) {
                    "Call_API" -> {
                        val api: String = data["api"] as String
                        val params: MutableMap<String, Any> = data["params"] as MutableMap<String, Any>
                        val seq: String = data["seq"] as String
                        call(api, params, seq)
                    }
                    "Disconnect" -> {
                        val errorCode = data["errorCode"] as Int
                        val errorMessage = data["errorMessage"] as String
                    }
                    else -> {
                        PluginNonebot.logger.warning("未知消息类型: ${Serializer.serialize(msg)}")
                        // TODO 其他操作
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

    private suspend fun DefaultClientWebSocketSession.outgoingMessages() {
        while (true) {
            try {
                send(messageChannel.receive())
            } catch (e: CancellationException) {
                delay(1000)
                println("消息发送线程被中断: ${e.localizedMessage}")
                PluginNonebot.instance.reload()
            } catch (e: Exception) {
                println("发送消息失败: ${e.localizedMessage}")
            }
        }
    }

    companion object : IWsConnection {
        override var session: WebSocketSession? = null
        override var isSessionValid: Boolean = false
        override var selfId: String = PluginNonebot.pluginConfig.config.rwsConnection!!.name
        override var messageChannel: Channel<String> = Channel()

    }
}