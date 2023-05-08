package fyi.fyw.mc.pluginnonebot.events

import fyi.fyw.mc.pluginnonebot.PluginNonebot
import fyi.fyw.mc.pluginnonebot.utils.Registry
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
        register(BroadcasterPlayerJoin())
        register(BroadcasterPlayerQuit())
        register(BroadcasterPlayerChat())
        register(BroadcasterServerChat())
        register(BroadcasterPlayerCommand())
        register(BroadcasterServerCommand())
        register(BroadcasterPlayerDeath())
    }
}
