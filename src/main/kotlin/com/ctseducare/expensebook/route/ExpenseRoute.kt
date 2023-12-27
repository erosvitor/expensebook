package com.ctseducare.expensebook.route

import com.ctseducare.expensebook.exception.DatabaseException
import com.ctseducare.expensebook.exception.ResourceNotFoundException
import com.ctseducare.expensebook.exception.response.ExceptionResponse
import com.ctseducare.expensebook.model.Expense
import com.ctseducare.expensebook.service.ExpenseService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.DI
import org.kodein.di.instance

fun Route.expense(di: DI) {

    val service by di.instance<ExpenseService>()

    route("/expenses") {
        post {
            val request = call.receive<Expense>()
            try {
                val response = service.create(request)
                call.respond(HttpStatusCode.OK, response)
            } catch (e: DatabaseException) {
                call.respond(HttpStatusCode.InternalServerError, e.message)
            }
        }
        get {
            try {
                val response = service.readAll()
                call.respond(HttpStatusCode.OK, response)
            } catch (e: DatabaseException) {
                call.respond(HttpStatusCode.InternalServerError, e.message)
            }
        }
        put {
            val request = call.receive<Expense>()
            try {
                val response = service.update(request)
                call.respond(HttpStatusCode.OK, response)
            } catch (e1: ResourceNotFoundException) {
                call.respond(HttpStatusCode.NotFound, ExceptionResponse(HttpStatusCode.NotFound.value, e1.message))
            } catch (e2: DatabaseException) {
                call.respond(HttpStatusCode.InternalServerError, e2.message)
            }
        }
        route("/{id}") {
            delete {
                val id = call.parameters["id"]?.toInt() ?: 0
                try {
                    val response = service.delete(id)
                    call.respond(HttpStatusCode.OK, response)
                } catch (e: ResourceNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, ExceptionResponse(HttpStatusCode.NotFound.value, e.message))
                } catch (e2: DatabaseException) {
                    call.respond(HttpStatusCode.InternalServerError, e2.message)
                }
            }

        }
    }

}