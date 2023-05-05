package fyi.fyw.mc.pluginnonebot.models.event

import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer

class EventPlayerQuit(val player: NSimplePlayer, val message: String) : BaseEvent
