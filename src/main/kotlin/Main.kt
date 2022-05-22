import blockchain.Block
import blockchain.Blockchain
import transaction.Transaction
import transaction.TransactionOutput
import wallet.Wallet

fun main(args: Array<String>) {

    val blockchain = Blockchain()
    val walletOne = Wallet.create(blockchain)
    val walletTwo = Wallet.create(blockchain)

    println("Wallet 1 balance: ${walletOne.balance}")
    println("Wallet 2 balance: ${walletTwo.balance}")

    val transactionOne = Transaction.create(sender = walletOne.publicKey, recipient = walletOne.publicKey, amount = 100)
    val output = TransactionOutput(recipient = walletOne.publicKey, amount = 100, transactionHash = transactionOne.hash)
    transactionOne.outputs.add(output)
    transactionOne.sign(walletOne.privateKey)

    var genesisBlock = Block(previousHash = "0")
    genesisBlock.addTransaction(transactionOne)
    genesisBlock = blockchain.add(genesisBlock)

    println("Wallet 1 balance: ${walletOne.balance}")
    println("Wallet 2 balance: ${walletTwo.balance}")

    val transactionTwo = walletOne.sendFundsTo(recipient = walletTwo.publicKey, amountToSend = 33)
    val transactionBlock = Block(genesisBlock.hash)
    transactionBlock.addTransaction(transactionTwo)
    val secondBlock = blockchain.add(transactionBlock)

    println("Wallet 1 balance: ${walletOne.balance}")
    println("Wallet 2 balance: ${walletTwo.balance}")
}