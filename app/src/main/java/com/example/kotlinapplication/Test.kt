package com.example.kotlinapplication

class Test {
    private var name: String? = "234354"
    private var ahaha:String? = "rebase nao ae"

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}