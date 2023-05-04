package fyi.fyw.mc.pluginnonebot.events

import fyi.fyw.mc.pluginnonebot.PluginNonebot
import fyi.fyw.mc.pluginnonebot.models.NEventBase
import fyi.fyw.mc.pluginnonebot.models.NEventFrame
import fyi.fyw.mc.pluginnonebot.registry.WebsocketsRegistry
import org.bukkit.event.Listener
import java.util.*

abstract class EventBroadcaster : Listener {
    val id: String
        get() = this::class.simpleName!!

    private fun broadcast(message: String) {
        WebsocketsRegistry.broadcast(message)
    }

    protected fun broadcast(message: NEventBase) {
        // TODO save to event database
        broadcast(gson.toJson(NEventFrame(UUID.randomUUID().toString(), System.currentTimeMillis(), message)))
    }

    companion object {
        val gson = PluginNonebot.gson
    }
}
