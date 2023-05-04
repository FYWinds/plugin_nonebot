package fyi.fyw.mc.pluginnonebot.models.api

class BaseApiResultFrame(
    val status: String = "ok",
    val retcode: Int = 0,
    val message: String = "",
    val data: BaseApiResult? = null,
    val echo: Int,
)
