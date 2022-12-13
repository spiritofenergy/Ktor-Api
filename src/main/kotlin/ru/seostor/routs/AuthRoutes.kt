package ru.seostor.routs

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.seostor.data.models.User
import ru.seostor.data.models.UserDataSource
import ru.seostor.data.models.requests.AuthRequest
import ru.seostor.security.hashing.HashingService

fun Route.singUp(
    hashingService: HashingService,
    userDataSource: UserDataSource,

){
    post ("signup"){
        val request = call.receiveOrNull<AuthRequest>() ?:kotlin.run{
            call.respond(HttpStatusCode.BadRequest)
            return@post
    }
        val areFieldsBlank = request.username.isBlank() || request.password.isBlank()
        val isPasswordShort = request.password.length < 8

        if (userDataSource.getUserByUsername(request.username) != null){
            call.respond(HttpStatusCode.Conflict,"Username is already exists")
            return@post
        }
        if (areFieldsBlank || isPasswordShort){
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        val saltedHash = hashingService.generateSaltedHash(request.password)
        val user = User(
            username = request.username,
            password = request.password,
            salt = saltedHash.salt
        )
        val wasAcknowLedged = userDataSource.insertUser(user)
        if (!wasAcknowLedged){
            call.respond(HttpStatusCode.Conflict)
            return@post
        }else{
            call.respond(HttpStatusCode.OK)
        }
    }
}