package com.example.plugins

import com.example.routing.userRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
    routing {
        userRoutes()
    }
}