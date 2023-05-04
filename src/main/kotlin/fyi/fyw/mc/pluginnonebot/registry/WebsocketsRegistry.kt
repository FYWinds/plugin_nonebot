package fyi.fyw.mc.pluginnonebot.registry

import fyi.fyw.mc.pluginnonebot.websockets.Websockets

object WebsocketsRegistry : Registry<Websockets> {
    override val values: MutableMap<String, Websockets> = mutableMapOf()

    override fun register(value: Websockets) {
        values[value.id] = value
    }

    override fun remove(value: Websockets) {
        values.remove(value.id)
    }

    override fun get(id: String): Websockets? {
        return values[id]
    }

    fun broadcast(message: String) {
        values.values.forEach { connection -> connection.broadcast(message) }
    }
}
