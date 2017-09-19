package jp.nephy.penicillin.exception

class UserMustVerifyLogin: AbsTwitterErrorMessage(231, "User must verify login", "Returned as a challenge in xAuth when the user has login verification enabled on their account and needs to be directed to twitter.com to generate a temporary password .")