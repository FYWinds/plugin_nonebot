package fyi.fyw.mc.pluginnonebot.events

import fyi.fyw.mc.pluginnonebot.events.models.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.events.models.EventPlayerCommand
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class BroadcasterPlayerCommand : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: PlayerCommandPreprocessEvent) {
        broadcast(
            BaseEventFrame(
                data = EventPlayerCommand.from(event),
                type = "command",
                detailType = "player_command",
            ),
        )
    }
}
