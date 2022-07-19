package cn.windis.pluginnonebot.config

import org.bukkit.configuration.ConfigurationSection

/**
 * @author FYWinds
 */
class WsConfigModel(section: ConfigurationSection) {
    var name: String
    var host: String
    var port: Int
    var token: String
    var timeout = 10000

    init {
        name = section.getString("name")!!
        host = section.getString("host")!!
        port = section.getInt("port")
        token = section.getString("token", "")!!
        timeout = section.getInt("timeout", 10000)
    }
}