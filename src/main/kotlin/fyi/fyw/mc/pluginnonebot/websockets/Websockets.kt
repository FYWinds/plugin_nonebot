package fyi.fyw.mc.pluginnonebot.websockets

interface Websockets {

    val id: String

    fun start()
    fun stop()
    fun broadcast(message: String)
}
