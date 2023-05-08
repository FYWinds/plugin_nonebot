package fyi.fyw.mc.pluginnonebot.events

import fyi.fyw.mc.pluginnonebot.events.models.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.events.models.EventPlayerAdvancement
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerAdvancementDoneEvent

class BroadcasterPlayerAdvancement : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: PlayerAdvancementDoneEvent) {
        broadcast(
            BaseEventFrame(
                data = EventPlayerAdvancement.from(event),
                type = "player",
                detailType = "player_advancement",
            ),
        )
    }
}
