package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException


@OptIn(ExperimentalSerializationApi::class)
fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(status = HttpStatusCode.InternalServerError, message = cause.stackTraceToString())
        }

        exception<BadRequestException> { call, cause ->
            when (val exception = cause.cause?.cause) {
                is MissingFieldException -> call.respond(
                    message = "Missing fields ${exception.missingFields}",
                    status = HttpStatusCode.BadRequest
                )

                else -> call.respond(message = cause.stackTraceToString(), status = HttpStatusCode.BadRequest)
            }
        }

        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
        }
    }
}