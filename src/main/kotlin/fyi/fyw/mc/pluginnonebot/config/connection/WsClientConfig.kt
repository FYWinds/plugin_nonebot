package fyi.fyw.mc.pluginnonebot.config.connection

class WsClientConfig(conn: Map<String, Map<String, *>>) : WsConfig(conn) {
    var address: String = ""
    var reconnectInterval: Int = 1000

    init {
        address = conn[id]!!["address"] as String
        reconnectInterval = conn[id]!!["reconnect-interval"] as Int
    }
}
