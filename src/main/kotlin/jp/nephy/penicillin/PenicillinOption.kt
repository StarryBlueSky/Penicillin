package jp.nephy.penicillin

import java.util.concurrent.TimeUnit

class PenicillinOption(val maxRetries: Int, val retryInterval: Long, val retryIntervalUnit: TimeUnit) {
    class Builder {
        private var maxRetries = 3
        fun maxRetries(count: Int) {
            maxRetries = count
        }

        private var retryInterval = 100L
        private var retryIntervalUnit = TimeUnit.MILLISECONDS
        fun retryInterval(interval: Long, unit: TimeUnit) {
            retryInterval = interval
            retryIntervalUnit = unit
        }

        fun build(): PenicillinOption {
            return PenicillinOption(maxRetries, retryInterval, retryIntervalUnit)
        }
    }
}
