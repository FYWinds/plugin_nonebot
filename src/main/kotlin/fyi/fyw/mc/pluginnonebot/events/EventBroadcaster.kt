package fyi.fyw.mc.pluginnonebot.events

import fyi.fyw.mc.pluginnonebot.PluginNonebot
import fyi.fyw.mc.pluginnonebot.events.models.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.websockets.WebsocketsRegistry
import org.bukkit.event.Listener

abstract class EventBroadcaster : Listener {
    val id: String
        get() = this::class.simpleName!!

    private fun broadcast(message: String) {
        WebsocketsRegistry.broadcast(message)
    }

    protected fun broadcast(message: BaseEventFrame) {
        // TODO save to event database
        broadcast(gson.toJson(message))
    }

    companion object {
        val gson = PluginNonebot.gson
    }
}
