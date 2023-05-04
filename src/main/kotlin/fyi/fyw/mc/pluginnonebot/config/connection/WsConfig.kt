package fyi.fyw.mc.pluginnonebot.config.connection

open class WsConfig(conn: Map<String, Map<String, *>>) {
    var id: String = ""
    var token: String = ""

    init {
        id = conn.keys.first()
        token = conn.values.first()["token"] as String
    }

    fun from(conn: Map<String, Map<String, *>>): WsConfig {
        return if (conn.keys.first().startsWith("ws-reverse")) {
            WsClientConfig(conn)
        } else if (conn.keys.first().startsWith("ws")) {
            WsServerConfig(conn)
        } else {
            throw Exception("Unknown connection type")
        }
    }
}
