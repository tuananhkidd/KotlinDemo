package com.example.kotlinapplication

class Test {
    private var name: String? = "123"

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}