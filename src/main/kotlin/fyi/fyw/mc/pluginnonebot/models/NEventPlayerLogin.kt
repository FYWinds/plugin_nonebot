package fyi.fyw.mc.pluginnonebot.models

class NEventPlayerLogin(
    val player: NSimplePlayer,
    val message: String,
) : NEventBase("login", "player_login", "")
