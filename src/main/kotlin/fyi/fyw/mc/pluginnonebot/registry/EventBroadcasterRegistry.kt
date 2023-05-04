package fyi.fyw.mc.pluginnonebot.registry

import fyi.fyw.mc.pluginnonebot.PluginNonebot
import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
import fyi.fyw.mc.pluginnonebot.events.broadcasters.BroadcasterPlayerLogin
import org.bukkit.Bukkit

object EventBroadcasterRegistry : Registry<EventBroadcaster> {
    override val values: MutableMap<String, EventBroadcaster> = mutableMapOf()
    fun load(plugin: PluginNonebot) {
        values.values.forEach { broadcaster -> Bukkit.getPluginManager().registerEvents(broadcaster, plugin) }
    }

    override fun register(value: EventBroadcaster) {
        values[value.id] = value
    }

    override fun remove(value: EventBroadcaster) {
        values.remove(value.id)
    }

    override fun get(id: String): EventBroadcaster? {
        return values[id]
    }

    fun init() {
        this.register(BroadcasterPlayerLogin())
    }
}
