package com.example.kotlinapplication

class Test {
    private var name: String? = ""
    private var ahihi1:String? = "rebase"

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}