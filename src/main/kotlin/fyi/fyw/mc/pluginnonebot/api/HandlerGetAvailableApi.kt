package fyi.fyw.mc.pluginnonebot.api

import fyi.fyw.mc.pluginnonebot.api.models.BaseApiResult
import fyi.fyw.mc.pluginnonebot.api.models.ResultGetAvailableApi

class HandlerGetAvailableApi : ApiHandler {
    override val id: String = "get_available_api"

    override fun handle(params: Map<String, Any>): BaseApiResult {
        val hidden = params.getOrDefault("hidden", false) as Boolean
        val apis = ApiHandlerRegistry.values.keys.filter {
            !it.startsWith("_") || hidden
        }
        return ResultGetAvailableApi(apis)
    }
}
