package fyi.fyw.mc.pluginnonebot.events

import fyi.fyw.mc.pluginnonebot.events.models.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.events.models.EventPlayerDeath
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.PlayerDeathEvent

class BroadcasterPlayerDeath : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    fun listen(event: PlayerDeathEvent) {
        broadcast(
            BaseEventFrame(
                data = EventPlayerDeath.from(event),
                type = "player",
                detailType = "player_death",
            ),
        )
    }
}
