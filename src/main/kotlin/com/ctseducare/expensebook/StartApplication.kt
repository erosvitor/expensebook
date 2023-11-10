package com.ctseducare.expensebook

import com.ctseducare.expensebook.di.daoModule
import com.ctseducare.expensebook.di.databaseModule
import com.ctseducare.expensebook.di.serviceModule
import com.ctseducare.expensebook.exception.response.ExceptionResponse
import com.ctseducare.expensebook.model.Expense
import com.ctseducare.expensebook.routing.configureRouting
import io.ktor.http.*
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.kodein.di.DI

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }

    install(RequestValidation) {
        validate<Expense> { expense ->
            if (expense.description.isNullOrBlank())
                ValidationResult.Invalid("O campo 'description' é obrigatório")
            else if (expense.value <= 0)
                ValidationResult.Invalid("O campo 'value' não pode ser menor ou igual a zero")
            else
                ValidationResult.Valid
        }
    }

    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest,  ExceptionResponse(400, cause.reasons.joinToString()))
        }
    }

    val di = DI {
        import(databaseModule)
        import(daoModule)
        import(serviceModule)
    }
    configureRouting(di)
}