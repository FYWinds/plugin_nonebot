package cn.windis.pluginnonebot.utils

import com.google.gson.Gson

class Serializer {
    companion object {
        private val gson: Gson = Gson()

        fun serialize(obj: Any): String {
            return gson.toJson(obj)
        }

        fun deserialize(json: String, clazz: Class<*>): Any {
            return gson.fromJson(json, clazz)
        }
    }
}