package fyi.fyw.mc.pluginnonebot.events

import fyi.fyw.mc.pluginnonebot.events.models.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.events.models.EventServerCommand
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.server.ServerCommandEvent

class BroadcasterServerCommand : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: ServerCommandEvent) {
        broadcast(
            BaseEventFrame(
                data = EventServerCommand.from(event),
                type = "command",
                detailType = "server_command",
            ),
        )
    }
}
