package jp.nephy.penicillin.core

import jp.nephy.penicillin.core.emulation.EmulationMode
import java.util.concurrent.TimeUnit

data class ClientOption(val maxRetries: Int, val retryInterval: Long, val retryIntervalUnit: TimeUnit, val emulationMode: EmulationMode)
