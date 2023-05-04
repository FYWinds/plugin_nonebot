package fyi.fyw.mc.pluginnonebot.config

import org.bukkit.configuration.file.YamlConfiguration

class ApiConfig(config: YamlConfiguration) {
    var rateLimit: Boolean = false
    var rateLimitFrequency: Int = 1
    var rateLimitBucket: Int = 1

    init {
        rateLimit = config.getBoolean("api.rate-limit.enabled", false)
        rateLimitFrequency = config.getInt("api.rate-limit.frequency", 1)
        rateLimitBucket = config.getInt("api.rate-limit.bucket", 1)
    }
}
