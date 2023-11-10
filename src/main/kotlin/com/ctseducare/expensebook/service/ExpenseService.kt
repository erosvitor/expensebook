package com.ctseducare.expensebook.service

import com.ctseducare.expensebook.dao.ExpenseDAO
import com.ctseducare.expensebook.exception.ResourceNotFoundException
import com.ctseducare.expensebook.model.Expense

class ExpenseService(private val dao: ExpenseDAO) {

    fun insert(expense: Expense) : Expense {
        dao.insert(expense)
        return expense
    }

    fun findAll() = dao.findAll()

    fun update(expense: Expense) : Expense {
        val expenseFounded = dao.findById(expense.id!!)
        if (expenseFounded == null) {
            throw ResourceNotFoundException("Expense do not exist!")
        } else {
            dao.update(expense)
            return expense
        }
    }

    fun delete(id: Int) : Expense {
        val expenseFounded = dao.findById(id)
        if (expenseFounded == null) {
            throw ResourceNotFoundException("Expense do not exist!")
        } else {
            dao.delete(id)
            return expenseFounded
        }
    }
/*
    fun findById(id: Int) : Expense {
        val expenseFounded = dao.findById(id)
        if (expenseFounded == null) {
            throw ResourceNotFoundException("Expense do not exist!")
        } else {
            return expenseFounded
        }
    }
*/
}