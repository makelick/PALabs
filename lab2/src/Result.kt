data class Result(val node: Node, val type: ResultType) {
    override fun toString(): String {
        return "$node\n$type"
    }
}