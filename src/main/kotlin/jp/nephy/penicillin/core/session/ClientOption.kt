package jp.nephy.penicillin.core.session

import jp.nephy.penicillin.core.emulation.EmulationMode

data class ClientOption(val maxRetries: Int, val retryInMillis: Long, val emulationMode: EmulationMode, val skipEmulationChecking: Boolean)
