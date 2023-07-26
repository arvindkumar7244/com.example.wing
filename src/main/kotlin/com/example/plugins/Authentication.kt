package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureAuthentication() {
    authentication {
        jwt {
            realm = "realm"
            verifier(
                JWT.require(Algorithm.HMAC256("secret"))
                    .withAudience("wing app audience")
                    .withIssuer("Wing App")
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim("id").asLong() > 0) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}