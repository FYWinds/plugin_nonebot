package fyi.fyw.mc.pluginnonebot.config.connection

class WsServerConfig(conn: Map<String, Map<String, *>>) : WsConfig(conn) {
    var host: String = ""
    var port: Int = 0
    var heartbeatInterval: Int = 5

    init {
        host = conn[id]!!["host"] as String
        port = conn[id]!!["port"] as Int
        heartbeatInterval = conn[id]!!["heartbeat-interval"] as Int
    }
}
