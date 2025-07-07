package com.example.authdemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform