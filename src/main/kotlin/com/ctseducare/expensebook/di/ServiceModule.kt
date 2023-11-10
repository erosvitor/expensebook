package com.ctseducare.expensebook.di

import com.ctseducare.expensebook.service.ExpenseService
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val serviceModule = DI.Module("service") {
    bindSingleton { ExpenseService(dao = instance()) }
}