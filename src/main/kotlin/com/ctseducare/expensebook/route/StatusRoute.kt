package com.ctseducare.expensebook.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.status() {
    route ("/status") {
        get {
            call.respond(HttpStatusCode.OK, "I am up!")
        }
    }
}
