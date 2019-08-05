package com.example.kotlinapplication

class Test {
    private var name: String? = "aaaaa"
    private var ahihi1:String? = "rebase"

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}