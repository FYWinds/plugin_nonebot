package cn.windis.pluginnonebot.webSocket.server

import cn.windis.pluginnonebot.PluginNonebot
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import java.time.Duration

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofMillis(PluginNonebot.pluginConfig.config.wsConnection!!.timeout.toLong())
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
}