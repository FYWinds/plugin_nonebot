package fyi.fyw.mc.pluginnonebot.models

import java.io.Serializable

class NEventFrame(
    val id: String, // Version 4 UUID
    val time: Long, // Unix timestamp
    val data: NEventBase,
) : Serializable
