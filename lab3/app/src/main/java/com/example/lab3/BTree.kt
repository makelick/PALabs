package com.example.lab3

import android.content.Context

class BTree(private val t: Int = 3) {

    private var root: Node = Node(t)

    fun search(context: Context, key: Int): String {
        val result = root.search(key)
        return if (result != null) context.getString(R.string.successful_search, result.toString())
        else context.getString(R.string.no_such_key)

    }

    fun insert(context: Context, record: Record): String {
        return if (root.search(record.key) == null) {
            var currentNode = if (root.records.size < 2 * t - 1) root else splitNode(root)
            while (!currentNode.isLeaf()) {
                val nextNode = currentNode.childByKey(record.key)
                currentNode =
                    if (nextNode.records.size < 2 * t - 1) nextNode else splitNode(nextNode)
            }
            currentNode.addRecord(record)
            context.getString(R.string.successful_insert)
        } else context.getString(R.string.unsuccessful_insert)
    }

    private fun splitNode(node: Node): Node {
        val midRecord = node.records[t - 1]

        val leftChildren = node.children.take(t).toMutableList()
        val leftRecords = node.records.take(t - 1).toMutableList()
        val leftNode = Node(t, node.parent, leftRecords, leftChildren)
        for (child in leftChildren) child.parent = leftNode

        val rightChildren = node.children.takeLast(t).toMutableList()
        val rightRecords = node.records.takeLast(t - 1).toMutableList()
        val rightNode = Node(t, node.parent, rightRecords, rightChildren)
        for (child in rightChildren) child.parent = rightNode

        return if (node.parent == null) {
            val newRootChildren = mutableListOf(leftNode, rightNode)
            val newRootRecords = mutableListOf(midRecord)
            val newRoot = Node(t, null, newRootRecords, newRootChildren)
            root = newRoot
            leftNode.parent = newRoot
            rightNode.parent = newRoot
            newRoot
        } else {
            val childIndex = node.parent!!.children.indexOf(node)
            node.parent!!.children.remove(node)
            node.parent!!.addRecord(midRecord)
            node.parent!!.children.addAll(childIndex, mutableListOf(leftNode, rightNode))
            node.parent!!
        }
    }

    fun update(context: Context, record: Record): String {
        return if (root.searchAndUpdate(record))
            context.getString(R.string.successful_update, record.key.toString())
        else context.getString(R.string.no_such_key)
    }

    fun delete(context: Context, key: Int): String {
        val record = root.search(key)
        return if (record != null) {
            root.delete(record)
            context.getString(R.string.successful_delete)
        } else context.getString(R.string.no_such_key)

    }
}