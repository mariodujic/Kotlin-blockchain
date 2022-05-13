fun main(args: Array<String>) {

    val blockChain = Blockchain()

    val originBlock = blockChain.add(Block(previousHash = "0", data = "I'm the origin block!"))
    val secondBlock = blockChain.add(Block(originBlock.hash, "I'm the second"))
    val thirdBlock = blockChain.add(Block(secondBlock.hash, "I'm the third"))

    println("BlockChain is valid: ${blockChain.isValid()}")
}