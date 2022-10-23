fun main() {
    val limit = 20
    val startState = Generator().generateState()
    println("Start state:\n$startState")
    val ldfs = LDFS(startState)
    println(ldfs.search(limit))
}