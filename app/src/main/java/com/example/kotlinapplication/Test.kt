package com.example.kotlinapplication

class Test {
    private var name: String? = "huhu 12 3 4"
    private var ahehe123:String? = "84739573489"

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}