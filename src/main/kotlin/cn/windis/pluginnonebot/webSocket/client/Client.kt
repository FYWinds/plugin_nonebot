@file:Suppress("UNCHECKED_CAST")

package cn.windis.pluginnonebot.webSocket.client

import cn.windis.pluginnonebot.PluginNonebot
import cn.windis.pluginnonebot.utils.Serializer
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import java.net.ConnectException

class Client(server: String, retryInterval: Int, maxRetries: Int) {
    private val client = HttpClient(CIO) {
        install(WebSockets)
    }

    private var sThread: Thread = Thread {}

    private lateinit var socket: DefaultWebSocketSession
    private var isValid: Boolean = false

    init {
        sThread = Thread {
            runBlocking {
                try {
                    withContext(Dispatchers.IO) {
                        when (maxRetries) {
                            -1 -> {
                                while (true) {
                                    try {
                                        connect(server)
                                        break
                                    } catch (e: ConnectException) {
                                        PluginNonebot.logger.warning("连接失败，正在重试...")
                                        Thread.sleep(retryInterval.toLong())
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
                                        Thread.sleep(retryInterval.toLong())
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
                                        Thread.sleep(retryInterval.toLong())
                                    }
                                }
                            }
                        }
                    }
                } catch (_: InterruptedException) {
                }
            }
        }
        sThread.start()
    }

    fun stop() {
        client.close()
        sThread.interrupt()
    }

    private suspend fun connect(server: String) {
        client.ws(server) {
            socket = this
            val messageOutputRoutine = launch { outputMessages() }

            messageOutputRoutine.cancelAndJoin()
        }
    }

    private suspend fun DefaultClientWebSocketSession.outputMessages() {
        try {
            for (message in incoming) {
                message as? Frame.Text ?: continue
                val msg: Map<Any, Any> = Serializer.deserialize(message.readText(), Map::class.java) as Map<Any, Any>
                val action: String = msg["action"] as String
                val data: Map<Any, Any> = msg["data"] as Map<Any, Any>

                when (action) {
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
        } catch (e: Exception) {
            println("Error while receiving: " + e.localizedMessage)
        }
    }

    suspend fun broadcast(message: Map<Any, Any>) {
        if (isValid) {
            socket.send(Serializer.serialize(message))
        }
    }
}