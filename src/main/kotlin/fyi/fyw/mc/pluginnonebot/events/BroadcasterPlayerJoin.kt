package fyi.fyw.mc.pluginnonebot.events

import fyi.fyw.mc.pluginnonebot.events.models.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.events.models.EventPlayerJoin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent

class BroadcasterPlayerJoin : EventBroadcaster() {

    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: PlayerJoinEvent) {
        broadcast(
            BaseEventFrame(
                data = EventPlayerJoin.from(event),
                type = "player",
                detailType = "player_join",
            ),

        )
    }
}
