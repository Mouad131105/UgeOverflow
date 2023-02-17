package fr.uge.ugeoverflow.api

class ApiException(val code: Int, message: String) : Exception(message)