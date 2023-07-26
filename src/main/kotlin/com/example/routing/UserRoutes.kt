package com.example.routing

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.repository
import com.example.resources.Profile
import com.example.resources.SignUp
import com.example.serializers.SignupUser
import com.example.serializers.toUser
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Routing.userRoutes() {

    install(RequestValidation) {
        validate<SignupUser> {
            if (it.email.isBlank()) {
                ValidationResult.Invalid("Email is empty")
            } else if (it.password.isBlank()) {
                ValidationResult.Invalid("Password is empty")
            } else if (it.name.isBlank()) {
                ValidationResult.Invalid("Name is empty")
            } else ValidationResult.Valid
        }
    }

    post<SignUp, SignupUser> { _, user ->
        val id = repository.insertUser(user.toUser())
        val token = JWT.create()
            .withAudience("wing app audience")
            .withIssuer("Wing App")
            .withClaim("id", id)
            .sign(Algorithm.HMAC256("secret"))
        call.respond(mapOf("token" to token))
    }

    authenticate {
        get<Profile> {
            val principal = call.principal<JWTPrincipal>()
            val id = principal!!.payload.getClaim("id").asLong()
            val user = repository.getUser(id)
            if (user == null) {
                call.respond(status = HttpStatusCode.BadRequest, message = "No user with id $id")
            } else {
                call.respond(user)
            }
        }
    }

}
