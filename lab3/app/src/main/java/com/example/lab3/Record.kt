package com.example.lab3

import java.io.Serializable

class Record(val key: Int, var data : String) : Serializable {
    override fun toString(): String {
        return "$key : \"$data\""
    }
}