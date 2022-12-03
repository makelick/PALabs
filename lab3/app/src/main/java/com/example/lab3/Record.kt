package com.example.lab3

class Record(val key: Int, var data : String) {
    override fun toString(): String {
        return "$key : \"$data\""
    }
}