fun main() {

    val originBlock = Block(
        previousHash = "0",
        data = "First block"
    )
    val secondBlock = Block(
        previousHash = originBlock.hash,
        data = "Second block"
    )
    val thirdBlock = Block(
        previousHash = secondBlock.hash,
        data = "Third block"
    )

    println(originBlock)
    println(secondBlock)
    println(thirdBlock)
}