package cn.windis.pluginnonebot.config

import org.bukkit.configuration.ConfigurationSection

/**
 * @author FYWinds
 */
class RwsConfigModel(section: ConfigurationSection) {
    var name: String
    var server: String
    var token: String
    var timeout = 10000
    var retryInterval = 5000
    var maxRetries = 3

    init {
        name = section.getString("name")!!
        server = section.getString("server")!!
        token = section.getString("token")!!
        timeout = section.getInt("timeout", 10000)
        retryInterval = section.getInt("retry-interval", 5000)
        maxRetries = section.getInt("max-retries", 3)
    }
}