package fyi.fyw.mc.pluginnonebot.websockets

import fyi.fyw.mc.pluginnonebot.PluginNonebot
import fyi.fyw.mc.pluginnonebot.config.Config
import fyi.fyw.mc.pluginnonebot.config.connection.WsServerConfig
import fyi.fyw.mc.pluginnonebot.registry.ApiHandlerRegistry
import fyi.fyw.mc.pluginnonebot.registry.WebsocketsRegistry
import fyi.fyw.mc.pluginnonebot.utils.NLogger
import org.java_websocket.WebSocket
import org.java_websocket.drafts.Draft
import org.java_websocket.exceptions.InvalidDataException
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.handshake.ServerHandshakeBuilder
import java.net.InetSocketAddress
import org.java_websocket.server.WebSocketServer as WsServer

class NWebSocketServer(conn: WsServerConfig) : Websockets, WsServer(InetSocketAddress(conn.host, conn.port)) {
    override val id: String = conn.id
    private val host: String = conn.host
    private val port: Int = conn.port
    private val pingPeriod: Int = Config.INSTANCE.heartbeatInterval
    private val token: String = conn.token
    private val logger = NLogger(id)
    private val gson = PluginNonebot.gson

    override fun start() {
        connectionLostTimeout = pingPeriod
        super.start()
    }

    override fun stop() {
        WebsocketsRegistry.remove(this)
        super.stop()
    }

    override fun broadcast(message: String) {
        super.broadcast(message)
    }

    @Throws(InvalidDataException::class)
    override fun onWebsocketHandshakeReceivedAsServer(
        conn: WebSocket,
        draft: Draft,
        request: ClientHandshake,
    ): ServerHandshakeBuilder? {
        if (token.isNotEmpty()) {
            val remoteToken = request.getFieldValue("Authorization")?.split(" ")?.get(1) ?: ""
            // Still considering whether to log the token
            logger.debug("Remote connection ${conn.remoteSocketAddress.hostString} trying to connect with token $remoteToken")
            if (token != remoteToken) {
                throw InvalidDataException(401, "Unauthorized") // Reject the connection
            }
        }
        logger.debug("Acceptted connection from ${conn.remoteSocketAddress.hostString}")
        return super.onWebsocketHandshakeReceivedAsServer(conn, draft, request)
    }

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        logger.info("${conn.remoteSocketAddress.hostString} Connected")
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String, remote: Boolean) {
        logger.info("Connection closed by ${if (remote) "remote peer" else "us"}. Code: $code Reason: $reason.")
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        try {
            @Suppress("UNCHECKED_CAST")
            ApiHandlerRegistry.call(gson.fromJson(message, Map::class.java) as Map<String, Any>, conn!!)
        } catch (_: TypeCastException) {
            // TODO but should assume this will not happen
        }
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        logger.error(ex)
    }

    override fun onStart() {
        WebsocketsRegistry.register(this)
        logger.info("Starting websocket server listening on $host:$port")
    }
}
