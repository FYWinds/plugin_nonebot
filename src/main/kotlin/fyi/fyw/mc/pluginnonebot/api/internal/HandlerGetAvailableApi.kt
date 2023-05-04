package fyi.fyw.mc.pluginnonebot.api.internal

import fyi.fyw.mc.pluginnonebot.api.ApiHandler
import fyi.fyw.mc.pluginnonebot.models.api.BaseApiResult
import fyi.fyw.mc.pluginnonebot.models.api.ResultGetAvailableApi
import fyi.fyw.mc.pluginnonebot.registry.ApiHandlerRegistry

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
