package com.example

import com.example.data.user.UserDaoImpl
import com.example.data.user.UserRepository
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

val repository = UserRepository(UserDaoImpl())

fun Application.module() {
    configureAuthentication()
    configureLogging()
    configureSerializer()
    configureResources()
    configureStatusPages()
    configureRouting()
}

