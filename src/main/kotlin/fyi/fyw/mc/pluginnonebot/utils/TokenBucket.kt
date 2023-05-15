package fyi.fyw.mc.pluginnonebot.utils

class TokenBucket(private var capacity: Long, private var windowTimeInSeconds: Long) {

    private var lastRefillTimeStamp: Long = System.currentTimeMillis()

    private var refillCountPerSecond: Long = capacity / windowTimeInSeconds

    private var availableTokens: Long = 0

    fun getAvailableTokens(): Long {
        return availableTokens
    }

    @Synchronized
    fun tryConsume(): Boolean {
        refill()
        return if (availableTokens > 0) {
            --availableTokens
            true
        } else {
            false
        }
    }

    @Synchronized
    private fun refill() {
        val now = System.currentTimeMillis()
        if (now > lastRefillTimeStamp) {
            val elapsedTime = now - lastRefillTimeStamp
            val tokensToBeAdded = (elapsedTime / 1000) * refillCountPerSecond
            if (tokensToBeAdded > 0) {
                availableTokens = (availableTokens + tokensToBeAdded).coerceAtMost(capacity)
                lastRefillTimeStamp = now
            }
        }
    }
}
