package fyi.fyw.mc.pluginnonebot.models.event

import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer

class EventPlayerChat(
    val sender: NSimplePlayer,
    val recipients: Set<NSimplePlayer>,
    val message: String,
    val cancelled: Boolean,
) : BaseEvent
