package com.learning.crudtwo.dto.request

data class PatchEmployeeRequest(
    val name: String? = null,
    val email: String? = null
)
