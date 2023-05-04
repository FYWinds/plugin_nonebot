package fyi.fyw.mc.pluginnonebot.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class NEventBase(
    val type: String,
    @SerializedName("detail_type") val detailType: String,
    @SerializedName("sub_type") val subType: String,
) : Serializable
