package cn.windis.pluginnonebot.api

class CallApi {
    companion object {
        fun callAPI(api: String, params: Map<String, Any>): Map<String, Any> {
            val result: MutableMap<String, Any> = mutableMapOf()
            when (api) {
                "send_command" -> {
                    sendCommand(params["command"] as String)
                }
                "send_message" -> {
                    sendMessage(params["message"] as String)
                }
                else -> {
                    result["code"] = 101
                    result["message"] = "NoSuchAPI"
                }
            }
            result["code"] = 0
            result["message"] = ""
            return result
        }
    }
}