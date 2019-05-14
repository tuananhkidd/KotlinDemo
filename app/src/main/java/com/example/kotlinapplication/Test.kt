package com.example.kotlinapplication

class Test {
    private var name: String? = ""

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}