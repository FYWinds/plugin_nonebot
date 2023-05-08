package fyi.fyw.mc.pluginnonebot.api

import fyi.fyw.mc.pluginnonebot.PluginNonebot
import fyi.fyw.mc.pluginnonebot.api.models.BaseApiResultFrame
import fyi.fyw.mc.pluginnonebot.utils.Registry
import org.java_websocket.WebSocket

object ApiHandlerRegistry : Registry<ApiHandler> {
    override val values: MutableMap<String, ApiHandler> = mutableMapOf()
    val gson = PluginNonebot.gson

    override fun register(value: ApiHandler) {
        values[value.id] = value
    }

    override fun remove(value: ApiHandler) {
        values.remove(value.id)
    }

    override fun get(id: String): ApiHandler? {
        return values[id]
    }

    fun call(message: Map<String, Any>, conn: WebSocket) {
        if (message["echo"] !is Double || message["action"] !is String || message["params"] !is Map<*, *>) {
            conn.send(
                gson.toJson(
                    BaseApiResultFrame(
                        "failed",
                        RetCode.BadRequest.value,
                        "${RetCode.BadRequest.message}: Can't validate echo, action or params",
                        null,
                        -1,
                    ),
                ),
            )
            return
        }
        val echo = (message["echo"] as Double).toInt()
        val action = message["action"] as String

        if (action !in values.keys) {
            conn.send(
                gson.toJson(
                    BaseApiResultFrame(
                        "failed",
                        RetCode.UnsupportedAction.value,
                        "${RetCode.UnsupportedAction.message}: $action",
                        null,
                        echo,
                    ),
                ),
            )
            return
        }
        val handler = values[action]!! // Already checked

        @Suppress("UNCHECKED_CAST")
        val params = message["params"] as Map<String, Any>
        try {
            val result = handler.handle(params)
            conn.send(gson.toJson(BaseApiResultFrame(data = result, echo = echo)))
        } catch (e: TypeCastException) {
            conn.send(
                gson.toJson(
                    BaseApiResultFrame(
                        "failed",
                        RetCode.BadParam.value,
                        "${RetCode.BadParam.message}: ${e.message}",
                        null,
                        echo,
                    ),
                ),
            )
        } catch (e: Exception) {
            conn.send(
                gson.toJson(
                    BaseApiResultFrame(
                        "failed",
                        RetCode.InternalHandlerError.value,
                        "${RetCode.InternalHandlerError.message} ${e.javaClass.simpleName}: ${e.message}",
                        null,
                        echo,
                    ),
                ),
            )
        }
    }

    enum class RetCode(val value: Int, val message: String) {
        BadRequest(10001, "Bad Request"),
        UnsupportedAction(10002, "Unsupported Action"),
        BadParam(10003, "Bad Param"),
        UnsupportedParam(10004, "Unsupported Param"),
        InternalHandlerError(20002, "Internal Handler Error"),
    }

    fun init() {
        register(HandlerGetAvailableApi())
        register(HandlerGetPlayerList())
        register(HandlerGetPlayerInfo())
        register(HandlerGetPlayerInventory())
    }
}
