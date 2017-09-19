package jp.nephy.penicillin.exception

class ApplicationCannotPerformWriteActions: AbsTwitterErrorMessage(261, "Application cannot perform write actions.", "Corresponds with HTTP 403 — thrown when the application is restricted from POST, PUT, or DELETE actions. See How to appeal application suspension and other disciplinary actions .")