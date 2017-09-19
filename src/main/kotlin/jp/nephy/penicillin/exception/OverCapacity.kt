package jp.nephy.penicillin.exception

class OverCapacity: AbsTwitterErrorMessage(130, "Over capacity", "Corresponds with HTTP 503 - Twitter is temporarily over capacity.")