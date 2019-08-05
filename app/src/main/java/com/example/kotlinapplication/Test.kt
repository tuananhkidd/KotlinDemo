package com.example.kotlinapplication

class Test {
    private var name: String? = "huhu"
    private var ahehe:String? = "1111111"

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}