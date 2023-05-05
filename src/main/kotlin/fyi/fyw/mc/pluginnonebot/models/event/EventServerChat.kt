package fyi.fyw.mc.pluginnonebot.models.event

import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer

class EventServerChat(val recipients: Set<NSimplePlayer>, val message: String, val cancelled: Boolean) : BaseEvent
