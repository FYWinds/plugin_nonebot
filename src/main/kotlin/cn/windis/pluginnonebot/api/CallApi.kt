package cn.windis.pluginnonebot.api

import cn.windis.pluginnonebot.utils.Serializer

class CallApi {
    companion object {
        fun callAPI(api: String, params: Map<String, Any>): MutableMap<String, Any> {
            val result: MutableMap<String, Any> = mutableMapOf()
            result["data"] = mutableMapOf<String, Any>()
            when (api) {
                "send_command" -> {
                    result["data"] = mutableMapOf("success" to sendCommand(params["command"] as String))
                }
                "send_message" -> {
                    sendMessage(params["message"] as String)
                }
                "get_player_info" -> {
                    val nData = getPlayerInfo(params["id"] as String)?.let { Serializer.serialize(it) }
                    if (nData == null) {
                        result["code"] = 201
                        result["message"] = "Player not found"
                    }
                    result["data"] = nData ?: ""
                }
                "get_player_list" -> {
                    result["data"] = Serializer.serialize(getPlayerList(online = params["online"] as Boolean))
                }
                else -> {
                    result["code"] = 101
                    result["message"] = "NoSuchAPI"
                }
            }
            result["code"] = result["code"] ?: 0
            result["message"] = result["message"] ?: ""
            return result
        }
    }
}