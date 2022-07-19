package cn.windis.pluginnonebot.webSocket.server

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.concurrent.TimeUnit

class Server(host: String, port: Int) {
    private val server: NettyApplicationEngine = embeddedServer(Netty, host = host, port = port) {
        configureSockets()
        configureRouting()
    }

    private var sThread: Thread = Thread(Runnable {
        server.start(wait = true)
    })

    init {
        sThread.start()
    }

    fun stop() {
        server.stop(1000, 1000, TimeUnit.MILLISECONDS)
        sThread.interrupt()
    }
}
