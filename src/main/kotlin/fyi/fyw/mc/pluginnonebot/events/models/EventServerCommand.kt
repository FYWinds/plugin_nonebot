package fyi.fyw.mc.pluginnonebot.events.models

import fyi.fyw.mc.pluginnonebot.models.NSimpleCommandSender
import org.bukkit.event.server.ServerCommandEvent

class EventServerCommand(
    val sender: NSimpleCommandSender,
    val command: String,
    val cancelled: Boolean,
) : BaseEvent {
    companion object {
        fun from(event: ServerCommandEvent): EventServerCommand {
            return EventServerCommand(
                NSimpleCommandSender.from(event.sender),
                event.command,
                event.isCancelled,
            )
        }
    }
}
