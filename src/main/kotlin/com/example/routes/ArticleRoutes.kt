package com.example.routes

import com.example.dao.dao
import com.example.models.Article
import io.ktor.server.application.call
import io.ktor.server.freemarker.FreeMarkerContent
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.util.getOrFail

fun Route.articleRouting() {
    route("/article") {
        get {
            val articles = dao.allArticles()
            call.respond(articles)
        }

        post {
            try {
                val article = call.receive<Article>()
                val body = article.body
                val title = article.title

                val articleDao = dao.addNewArticle(title, body)
                call.respondRedirect("/article/${articleDao?.id}")
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }

        get("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val article = dao.article(id)!!
            call.respond(article)
        }
        get("{id}/edit") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("edit.ftl", mapOf("article" to dao.article(id))))
        }

        post("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            when (formParameters.getOrFail("_action")) {
                "update" -> {
                    val title = formParameters.getOrFail("title")
                    val body = formParameters.getOrFail("body")
                    dao.editArticle(id, title, body)
                    call.respondRedirect("/articles/$id")
                }

                "delete" -> {
                    dao.deleteArticle(id)
                    call.respondRedirect("/articles")
                }
            }
        }
    }
}