package com.example.lab3

class Node(var parent: Node? = null,
           var records : MutableList<Record> = mutableListOf(),
           var children : MutableList<Node> = mutableListOf()) {

    fun isLeaf() = children.isEmpty()

    fun search(key: Int) : Record? {
        val result = binarySearch(key)
        if (result != null) return result
        if (this.isLeaf()) return null
        return childByKey(key).search(key)
    }

    fun searchAndUpdate(record: Record) : Boolean {
        val result = binarySearch(record.key)?.let { it.data = record.data }
        if (result != null) return true
        if (this.isLeaf()) return false
        return childByKey(record.key).searchAndUpdate(record)
    }

    private fun binarySearch(key : Int) : Record? {
        var len = records.size
        var mid = len / 2
        while (len != 0) {
            len /= 2
            if ( mid >= 0 && (mid >= records.size || records[mid].key > key)) mid -= len / 2 + 1
            else if (mid < 0 || records[mid].key < key) mid += len / 2 + 1
            else if (records[mid].key == key) return records[mid]
        }
        return null
    }

    fun childByKey(key: Int): Node{
        for (i in records.indices){
            if (records[i].key >= key){
                return children[i]
            }
        }
        return children.last()
    }

    fun addRecord(record: Record) {
        records.add(record)
        records.sortBy { it.key }
    }



}