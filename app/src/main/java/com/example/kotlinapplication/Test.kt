package com.example.kotlinapplication

class Test {
    private var name1: String? = "42342342"

    fun getName(): String {
        return name1!!
    }

    constructor(name: String) {
        this.name1 = name
    }

}