import java.math.BigInteger
import java.security.MessageDigest

fun String.hash(algorith: String = "SHA-256"): String {
    val messageDigest = MessageDigest.getInstance(algorith)
    messageDigest.update(this.toByteArray())
    return String.format("%064x", BigInteger(1, messageDigest.digest()))
}