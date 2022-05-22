package wallet

import blockchain.Blockchain
import transaction.Transaction
import transaction.TransactionOutput
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey

data class Wallet(
    val publicKey: PublicKey,
    val privateKey: PrivateKey,
    val blockchain: Blockchain
) {

    companion object {

        fun create(blockchain: Blockchain): Wallet {
            val generator = KeyPairGenerator.getInstance("RSA")
            generator.initialize(2048)
            val keyPair = generator.genKeyPair()

            return Wallet(
                publicKey = keyPair.public,
                privateKey = keyPair.private,
                blockchain = blockchain
            )
        }
    }

    val balance: Int
        get() {
            return getMyTransactions().sumBy { it.amount }
        }

    private fun getMyTransactions(): Collection<TransactionOutput> {
        return blockchain.UTXO.filterValues { it.isMine(publicKey) }.values
    }

    fun sendFundsTo(recipient: PublicKey, amountToSend: Int): Transaction {

        if (amountToSend > balance) {
            throw IllegalArgumentException("Insufficient funds")
        }

        val transaction = Transaction.create(sender = publicKey, recipient = publicKey, amount = amountToSend)
        transaction.outputs.add(
            TransactionOutput(
                recipient = recipient,
                amount = amountToSend,
                transactionHash = transaction.hash
            )
        )

        var collectedAmount = 0
        for (myTx in getMyTransactions()) {
            collectedAmount += myTx.amount
            transaction.inputs.add(myTx)

            if (collectedAmount > amountToSend) {
                val change = collectedAmount - amountToSend
                transaction.outputs.add(
                    TransactionOutput(
                        recipient = publicKey,
                        amount = change,
                        transactionHash = transaction.hash
                    )
                )
            }

            if (collectedAmount >= amountToSend) {
                break
            }
        }
        return transaction.sign(privateKey)
    }
}
