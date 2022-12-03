package com.example.lab3

class BTree(private var root : Node = Node()) {

    private val t = 3

    fun search(key: Int) : String {
        val result = root.search(key) ?: return ":("
        return "Found $result"
    }

    fun insert(record: Record) : String {
        var x = if (root.records.size < 2 * t - 1) root else splitNode(root)
        while (!x.isLeaf()) {
            val y = x.childByKey(record.key)
            x = if (y.records.size < 2 * t - 1) y else splitNode(y)
        }
        x.addRecord(record)
        return "Success"
    }

    private fun splitNode(node: Node) : Node {
        val midRecord = node.records[t - 1]

        val leftChildren = node.children.take(t).toMutableList()
        val leftRecords = node.records.take(t - 1).toMutableList()
        val leftNode = Node(node.parent, leftRecords, leftChildren)
        for (child in leftChildren) child.parent = leftNode

        val rightChildren = node.children.takeLast(t).toMutableList()
        val rightRecords = node.records.takeLast(t - 1).toMutableList()
        val rightNode = Node(node.parent, rightRecords, rightChildren)
        for (child in rightChildren) child.parent = rightNode

        if (node.parent == null) {
            val newRootChildren = mutableListOf(leftNode, rightNode)
            val newRootRecords = mutableListOf(midRecord)
            val newRoot = Node(Node(), newRootRecords, newRootChildren)
            root = newRoot
            leftNode.parent = newRoot
            rightNode.parent = newRoot
            return newRoot
        }

        else {
            val childIndex = node.parent!!.children.indexOf(node)
            node.parent!!.children.remove(node)
            node.parent!!.addRecord(midRecord)
            node.parent!!.children.addAll(childIndex, mutableListOf(leftNode, rightNode))
            return node.parent!!
        }
    }

}