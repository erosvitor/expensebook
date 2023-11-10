package com.ctseducare.expensebook.di

import com.ctseducare.expensebook.dao.ExpenseDAO
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val daoModule = DI.Module("dao") {
    bindSingleton {
        ExpenseDAO(
            dataSource = instance()
        )
    }
}
