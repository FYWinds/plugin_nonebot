package fyi.fyw.mc.pluginnonebot.models.event

import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer

class EventPlayerCommand(
    val sender: NSimplePlayer,
    val command: String,
    val cancelled: Boolean,
) : BaseEvent
