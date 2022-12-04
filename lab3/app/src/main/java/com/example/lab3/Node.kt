package com.example.lab3

import java.io.Serializable

class Node(
    private val t: Int,
    var parent: Node? = null,
    var records: MutableList<Record> = mutableListOf(),
    var children: MutableList<Node> = mutableListOf()
    ) : Serializable {

    fun isLeaf() = children.isEmpty()

    fun search(key: Int): Record? {
        val result = binarySearch(key)
        if (result != null) return result
        if (this.isLeaf()) return null
        return childByKey(key).search(key)
    }

    fun searchAndUpdate(record: Record): Boolean {
        val result = binarySearch(record.key)?.let { it.data = record.data }
        if (result != null) return true
        if (this.isLeaf()) return false
        return childByKey(record.key).searchAndUpdate(record)
    }

    private fun binarySearch(key: Int): Record? {
        var len = records.size
        var mid = len / 2
        while (len != 0) {
            len /= 2
            if (mid >= 0 && (mid >= records.size || records[mid].key > key)) mid -= len / 2 + 1
            else if (mid < 0 || records[mid].key < key) mid += len / 2 + 1
            else if (records[mid].key == key) return records[mid]
        }
        return null
    }

    fun childByKey(key: Int): Node {
        for (i in records.indices) {
            if (records[i].key >= key) {
                return children[i]
            }
        }
        return children.last()
    }

    fun addRecord(record: Record) {
        records.add(record)
        records.sortBy { it.key }
    }

    fun addAllRecords(records: MutableList<Record>) {
        this.records.addAll(records)
        records.sortBy { it.key }
    }

    fun delete(record: Record) {
        if (binarySearch(record.key) != null) {
            if (isLeaf()) {
                records.remove(record)
            } else {
                val leftChild = childByKey(record.key - 1)
                val rightChild = childByKey(record.key + 1)
                if (leftChild.records.size >= t) {
                    val predecessor = leftChild.records.last()
                    records.remove(record)
                    addRecord(predecessor)

                    leftChild.delete(predecessor)
                } else if (rightChild.records.size >= t) {
                    val successor = rightChild.records.first()
                    records.remove(record)
                    addRecord(successor)

                    rightChild.delete(successor)
                } else {
                    records.remove(record)
                    children.remove(rightChild)
                    leftChild.addRecord(record)
                    leftChild.addAllRecords(rightChild.records)
                    leftChild.children.addAll(rightChild.children)

                    leftChild.delete(record)
                }
            }
        } else {
            val nextNode = childByKey(record.key)
            if (nextNode.records.size < t) {
                val nextNodeIndex = children.indexOf(nextNode)
                val leftSibling =
                    if (nextNodeIndex - 1 >= 0) children[nextNodeIndex - 1]
                    else null
                val rightSibling =
                    if (nextNodeIndex + 1 <= children.lastIndex) children[nextNodeIndex + 1]
                    else null

                if (leftSibling != null && leftSibling.records.size >= t) {
                    nextNode.addRecord(records[nextNodeIndex - 1])
                    records.removeAt(nextNodeIndex - 1)
                    addRecord(leftSibling.records.last())
                    leftSibling.records.removeLast()
                } else if (rightSibling != null && rightSibling.records.size >= t) {
                    nextNode.addRecord(records[nextNodeIndex])
                    records.removeAt(nextNodeIndex)
                    addRecord(rightSibling.records.first())
                    rightSibling.records.removeFirst()
                } else {
                    if (leftSibling != null) {
                        nextNode.addRecord(records[nextNodeIndex - 1])
                        records.removeAt(nextNodeIndex - 1)
                        children.remove(leftSibling)
                        nextNode.addAllRecords(leftSibling.records)
                        nextNode.children.addAll(0, leftSibling.children)
                    } else if (rightSibling != null) {
                        nextNode.addRecord(records[nextNodeIndex])
                        records.removeAt(nextNodeIndex)
                        children.remove(rightSibling)
                        nextNode.addAllRecords(rightSibling.records)
                        nextNode.children.addAll(rightSibling.children)
                    }
                }
            }
            nextNode.delete(record)
        }
    }
}