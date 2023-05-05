package fyi.fyw.mc.pluginnonebot.models.event

import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer

class EventPlayerJoin(
    val player: NSimplePlayer,
    val message: String,
) : BaseEvent
