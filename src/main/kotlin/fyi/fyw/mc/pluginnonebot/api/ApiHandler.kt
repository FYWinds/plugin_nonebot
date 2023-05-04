package fyi.fyw.mc.pluginnonebot.api

import fyi.fyw.mc.pluginnonebot.PluginNonebot
import fyi.fyw.mc.pluginnonebot.models.api.BaseApiResult

interface ApiHandler {
    val id: String

    fun handle(params: Map<String, Any>): BaseApiResult

    companion object {
        val gson = PluginNonebot.gson
    }
}
