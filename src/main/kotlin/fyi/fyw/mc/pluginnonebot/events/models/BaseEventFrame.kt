package fyi.fyw.mc.pluginnonebot.events.models

import com.google.gson.annotations.SerializedName
import fyi.fyw.mc.pluginnonebot.PluginNonebot
import java.util.*

class BaseEventFrame(
    val self: String = PluginNonebot.loadedConfig.serverName,
    val id: String = UUID.randomUUID().toString(), // Version 4 UUID
    val time: Long = System.currentTimeMillis(), // Unix timestamp
    val data: BaseEvent,
    val type: String,
    @SerializedName("detail_type") val detailType: String,
    @SerializedName("sub_type") val subType: String = "",
)
