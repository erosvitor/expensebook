package com.ctseducare.expensebook.dao

import com.ctseducare.expensebook.exception.DatabaseException
import com.ctseducare.expensebook.model.Expense
import com.zaxxer.hikari.HikariDataSource
import mu.KotlinLogging
import java.sql.ResultSet
import java.sql.Timestamp

class ExpenseDAO(private val dataSource: HikariDataSource) {

    fun create(expense: Expense): Boolean {
        val connection = dataSource.connection
        val result: Boolean
        try {
            val sql = "INSERT INTO expenses (description, value, paid_at) VALUES (?, ?, ?)"

            val preparedStatement = connection.prepareStatement(sql, arrayOf("id"))
            preparedStatement.setString(1, expense.description)
            preparedStatement.setDouble(2, expense.value)
            preparedStatement.setTimestamp(3, Timestamp.valueOf(expense.paid_at.atStartOfDay()))

            result = preparedStatement.execute()
            val key = preparedStatement.generatedKeys
            if (key.next()) {
                expense.id = key.getInt(1)
            }
        } catch (e: Exception) {
            logger.error { e.message }
            throw DatabaseException("Ocorreu um erro ao inserir a despesa!")
        } finally {
            connection.close()
        }
        return result
    }

    fun readAll(): List<Expense> {
        val connection = dataSource.connection
        var resultSet: ResultSet? = null
        val list = mutableListOf<Expense>()
        try {
            val sql = "SELECT id, description, value, paid_at FROM expenses"

            val preparedStatement = connection.prepareStatement(sql)
            resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                list.add(buildExpenseObject(resultSet))
            }

        } catch(e: Exception) {
            logger.error { e.message }
            throw DatabaseException("Ocorreu um erro ao recuperar as despesas!")
        } finally {
            resultSet?.close()
            connection.close()
        }
        return list
    }

    private fun buildExpenseObject(resultSet: ResultSet) = Expense(
        id = resultSet.getInt("id"),
        description = resultSet.getString("description"),
        value = resultSet.getDouble("value"),
        paid_at = resultSet.getTimestamp("paid_at").toLocalDateTime().toLocalDate()
    )

    fun update(expense: Expense): Boolean {
        val connection = dataSource.connection
        val result: Boolean
        try {
            val sql = "UPDATE expenses SET description = ?, value = ?, paid_at = ? WHERE id = ?"

            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, expense.description)
            preparedStatement.setDouble(2, expense.value)
            preparedStatement.setTimestamp(3, Timestamp.valueOf(expense.paid_at.atStartOfDay()))
            preparedStatement.setInt(4, expense.id!!)

            result = preparedStatement.execute()
        } catch(e: Exception) {
            logger.error { e.message }
            throw DatabaseException("Ocorreu um erro ao atualizar a despesa!")
        } finally {
            connection.close()
        }
        return result
    }

    fun delete(id: Int): Boolean {
        val connection = dataSource.connection
        val result: Boolean
        try {
            val sql = "DELETE FROM expenses WHERE id = ?"

            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)

            result = preparedStatement.execute()
        } catch(e: Exception) {
            logger.error { e.message }
            throw DatabaseException("Ocorreu um erro ao remover a despesa!")
        } finally {
            connection.close()
        }
        return result
    }

    fun findById(id: Int): Expense? {
        val connection = dataSource.connection
        var resultSet: ResultSet? = null
        var result: Expense? = null
        try {
            val sql = "SELECT id, description, value, paid_at FROM expenses WHERE id = ?"

            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)

            resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                result = buildExpenseObject(resultSet)
            }
        } catch(e: Exception) {
            logger.error { e.message }
            throw DatabaseException("Ocorreu um erro ao localizar a despesa!")
        } finally {
            resultSet?.close()
            connection.close()
        }
        return result
    }

    private companion object {
        val logger = KotlinLogging.logger(this::class.qualifiedName!!)
    }

}
