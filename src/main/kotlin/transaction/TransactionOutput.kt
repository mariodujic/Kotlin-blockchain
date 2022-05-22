package transaction

import blockchain.hash
import wallet.encodeToString
import java.security.PublicKey

data class TransactionOutput(
    val recipient: PublicKey,
    val amount: Int,
    val transactionHash: String,
    var hash: String = ""
) {
    init {
        hash = "${recipient.encodeToString()}$amount$transactionHash".hash()
    }

    fun isMine(me: PublicKey): Boolean {
        return recipient == me
    }
}