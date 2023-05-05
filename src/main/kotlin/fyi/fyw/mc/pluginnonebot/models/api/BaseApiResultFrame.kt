package fyi.fyw.mc.pluginnonebot.models.api

import com.google.gson.annotations.SerializedName

class BaseApiResultFrame(
    val status: String = "ok",
    val retcode: Int = 0,
    @SerializedName("error_message") val errorMessage: String = "",
    val data: BaseApiResult? = null,
    val echo: Int,
)
