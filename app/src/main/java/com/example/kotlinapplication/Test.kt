package com.example.kotlinapplication

class Test {
    private var name: String? = "234354 2321 123"
    private var ahaha:String? = "rebase nao ae 23 21312"

    fun getName(): String {
        return name!!
    }

    constructor(name: String) {
        this.name = name
    }

}