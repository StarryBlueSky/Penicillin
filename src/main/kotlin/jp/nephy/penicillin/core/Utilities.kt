package jp.nephy.penicillin.core

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger("Penicillin.Performance")

internal inline fun <T> measurePerformance(label: String, block: () -> T): T {
    if (!logger.isTraceEnabled) {
        return block()
    }

    var result: T? = null
    val timeMs = measureTimeMillis {
        result = block()
    }

    logger.trace { "$label = $timeMs ms" }

    return result!!
}

internal inline fun measurePerformanceAsync(label: String, crossinline block: () -> Unit) {
    if (!logger.isTraceEnabled) {
        block()
        return
    }

    GlobalScope.launch {
        val timeMs = measureTimeMillis {
            block()
        }

        logger.trace { "$label = $timeMs ms" }
    }
}
