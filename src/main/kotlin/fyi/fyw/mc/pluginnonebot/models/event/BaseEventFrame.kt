package fyi.fyw.mc.pluginnonebot.models.event

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class BaseEventFrame(
    val id: String = UUID.randomUUID().toString(), // Version 4 UUID
    val time: Long = System.currentTimeMillis(), // Unix timestamp
    val data: BaseEvent? = null,
    val type: String,
    @SerializedName("detail_type") val detailType: String,
    @SerializedName("sub_type") val subType: String = "",
) : Serializable