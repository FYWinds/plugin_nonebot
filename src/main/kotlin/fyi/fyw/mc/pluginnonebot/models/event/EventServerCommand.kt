package fyi.fyw.mc.pluginnonebot.models.event

import fyi.fyw.mc.pluginnonebot.models.NSimpleCommandSender

class EventServerCommand(
    val sender: NSimpleCommandSender,
    val command: String,
    val cancelled: Boolean,
) : BaseEvent
