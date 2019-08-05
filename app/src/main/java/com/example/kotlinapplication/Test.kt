package com.example.kotlinapplication

class Test {
    private var name: String? = "huhu 12 3"
    private var ahuhu:String? = "123 434 "

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}