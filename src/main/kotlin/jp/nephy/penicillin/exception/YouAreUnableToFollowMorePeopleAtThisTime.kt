package jp.nephy.penicillin.exception

class YouAreUnableToFollowMorePeopleAtThisTime: AbstractTwitterErrorMessage(161, "You are unable to follow more people at this time", "Corresponds with HTTP 403 — thrown when a user cannot follow another user due to some kind of limit")
