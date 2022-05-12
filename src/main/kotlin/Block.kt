import java.time.Instant

data class Block(
    val previousHash: String,
    val data: String,
    val timeStamp: Long = Instant.now().toEpochMilli(),
    var hash: String = ""
) {

    init {
        hash = calculateHash()
    }

    private fun calculateHash(): String {
        return "$previousHash$timeStamp".hash()
    }
}