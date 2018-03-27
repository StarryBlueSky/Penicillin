package jp.nephy.penicillin.exception

class OverCapacity: AbstractTwitterErrorMessage(130, "Over capacity", "Corresponds with HTTP 503 - Twitter is temporarily over capacity.")
