package ru.seostor.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import ru.seostor.data.models.UserDataSource
import ru.seostor.security.hashing.HashingService
import ru.seostor.security.token.TokenConfig
import ru.seostor.security.token.TokenService

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {

    routing {
    }
}
