package ru.seostor.security.token

interface TokenService {
    fun generate(
        config: TokenConfig,
        vararg claim: TokenClaim
    ): String
}