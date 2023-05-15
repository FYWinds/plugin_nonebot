package fyi.fyw.mc.pluginnonebot.websockets

import fyi.fyw.mc.pluginnonebot.PluginNonebot
import fyi.fyw.mc.pluginnonebot.api.ApiHandlerRegistry
import fyi.fyw.mc.pluginnonebot.config.connection.WsClientConfig
import fyi.fyw.mc.pluginnonebot.utils.NLogger
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import kotlin.concurrent.thread

class NWebSocketClient(conn: WsClientConfig) : WebSocketClient(URI(conn.address)), Websockets {
    override val id: String = conn.id
    private val token: String = conn.token
    private val reconnectInterval: Int = conn.reconnectInterval
    private val logger = NLogger(id)
    private val gson = PluginNonebot.gson

    override fun start() {
        if (token.isNotEmpty()) this.addHeader("Authorization", "Bearer $token")
        this.addHeader("X-Self", PluginNonebot.loadedConfig.serverName)
        connect()
    }

    override fun stop() {
        closeBlocking()
    }

    override fun broadcast(message: String) {
        send(message)
    }

    override fun onOpen(handshakedata: ServerHandshake) {
        logger.info("Connection established with server ${connection.remoteSocketAddress}.")
        WebsocketsRegistry.register(this)
    }

    override fun onMessage(message: String) {
        try {
            @Suppress("UNCHECKED_CAST")
            ApiHandlerRegistry.call(gson.fromJson(message, Map::class.java) as Map<String, Any>, this.connection)
        } catch (_: TypeCastException) {
            // TODO but should assume this will not happen
        }
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        logger.info(
            "Connection closed by ${if (remote) "remote peer" else "us"}. Code: $code Reason: $reason." +
                (if (reconnectInterval > 0) " Will try to reconnect in ${reconnectInterval / 1000} seconds" else ""),
        )
        WebsocketsRegistry.remove(this)
        thread {
            Thread.sleep(reconnectInterval.toLong())
            reconnect()
        }
    }

    override fun onError(ex: Exception) {
        logger.error(ex)
    }
}
