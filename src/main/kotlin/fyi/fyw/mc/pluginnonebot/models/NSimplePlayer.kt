package fyi.fyw.mc.pluginnonebot.models

import java.io.Serializable

open class NSimplePlayer(
    val name: String,
    val uuid: String,
    val isOnline: Boolean,
    val location: NEntityLocation?,
) : Serializable
