package fyi.fyw.mc.pluginnonebot.registry

import fyi.fyw.mc.pluginnonebot.PluginNonebot
import fyi.fyw.mc.pluginnonebot.api.ApiHandler
import fyi.fyw.mc.pluginnonebot.api.internal.HandlerGetAvailableApi
import fyi.fyw.mc.pluginnonebot.api.internal.HandlerGetPlayerList
import fyi.fyw.mc.pluginnonebot.models.api.BaseApiResultFrame
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
        val response: BaseApiResultFrame = try {
            val echo = (message["echo"] as Double).toInt() // This should never fail
            val action = message["action"] as String
            try {
                val handler =
                    values[message["action"] as String] ?: throw IllegalArgumentException("Unsupported Action")

                @Suppress("UNCHECKED_CAST")
                val params = message["params"] as Map<String, Any>
                try {
                    val result = handler.handle(params)
                    BaseApiResultFrame(data = result, echo = echo)
                } catch (_: TypeCastException) {
                    BaseApiResultFrame(
                        "failed",
                        RetCode.UnsupportedParam.value,
                        RetCode.UnsupportedParam.message,
                        null,
                        echo,
                    )
                } catch (e: Exception) {
                    BaseApiResultFrame(
                        "failed",
                        RetCode.InternalHandlerError.value,
                        "${RetCode.InternalHandlerError.message}: ${e.message}",
                        null,
                        echo,
                    )
                }
            } catch (_: TypeCastException) {
                BaseApiResultFrame(
                    "failed",
                    RetCode.BadParam.value,
                    RetCode.BadParam.message,
                    null,
                    echo,
                )
            } catch (_: IllegalArgumentException) {
                BaseApiResultFrame(
                    "failed",
                    RetCode.UnsupportedAction.value,
                    "${RetCode.UnsupportedAction.message}: $action",
                    null,
                    echo,
                )
            }
        } catch (e: Exception) {
            BaseApiResultFrame(
                "failed",
                RetCode.BadRequest.value,
                "${RetCode.BadRequest.message}: ${e.message}",
                null,
                -1,
            )
        }
        conn.send(gson.toJson(response))
    }

    enum class RetCode(val value: Int, val message: String) {
        BadRequest(10001, "Bad Request"),
        UnsupportedAction(10002, "Unsupported Action"),
        BadParam(10003, "Bad Param"),
        UnsupportedParam(10004, "Unsupported Param"),
        InternalHandlerError(20002, "Internal Handler Error"),
    }

    fun init() {
        this.register(HandlerGetAvailableApi())
        this.register(HandlerGetPlayerList())
    }
}
