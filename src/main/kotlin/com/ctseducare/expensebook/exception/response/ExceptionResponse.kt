package com.ctseducare.expensebook.exception.response

import kotlinx.serialization.Serializable

@Serializable
class ExceptionResponse(val error: Int, val reason: String)