package ru.perelyginva.focusflow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform