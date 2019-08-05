package com.example.kotlinapplication

class Test {
    private var name: String? = "huhu"
    private var ahuhu:String? = "123"

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}