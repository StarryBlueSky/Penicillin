package jp.nephy.penicillin.exception

class ThisEndpointHasBeenRetiredAndShouldNotBeUsed: AbsTwitterErrorMessage(251, "This endpoint has been retired and should not be used.", "Corresponds to a HTTP request to a retired URL.")