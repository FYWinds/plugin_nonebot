package fyi.fyw.mc.pluginnonebot.config.connection

class WsServerConfig(conn: Map<String, Map<String, *>>) : WsConfig(conn) {
    var host: String = ""
    var port: Int = 0

    init {
        host = conn[id]!!["host"] as String
        port = conn[id]!!["port"] as Int
    }
}
