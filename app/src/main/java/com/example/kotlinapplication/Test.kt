package com.example.kotlinapplication

class Test {
    private var name: String? = "aaaaa"
    private var ahaha:String? = "rebase"

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}