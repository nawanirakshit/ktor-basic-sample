package com.example.plugins

import com.example.routes.articleRouting
import com.example.routes.customerRouting
import com.example.routes.getOrderRoute
import com.example.routes.listOrdersRoute
import com.example.routes.totalizeOrderRoute
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureRouting() {

    routing {
        customerRouting()
        listOrdersRoute()
        getOrderRoute()
        totalizeOrderRoute()
        articleRouting()
    }
}
