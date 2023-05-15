package fyi.fyw.mc.pluginnonebot.config

import org.bukkit.configuration.file.YamlConfiguration

class ApiConfig(config: YamlConfiguration) {
    var rateLimit: Boolean = false
    var rateLimitFrequency: Long = 1
    var rateLimitCapacity: Long = 1

    init {
        rateLimit = config.getBoolean("api.rate-limit.enabled", false)
        rateLimitFrequency = config.getLong("api.rate-limit.frequency", 1)
        rateLimitCapacity = config.getLong("api.rate-limit.bucket", 1)
    }
}
