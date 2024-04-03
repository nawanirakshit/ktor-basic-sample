package com.example.plugins

import freemarker.cache.ClassTemplateLoader
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.freemarker.FreeMarker
import io.ktor.server.freemarker.FreeMarkerContent
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    routing {
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
        get("/html-freemarker") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("name" to "Shalaga"), ""))
        }
    }
}
