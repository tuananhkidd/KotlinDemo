package com.example.kotlinapplication

class Test {
    private var name: String? = ""
    private var ahihi:String? = ""

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}