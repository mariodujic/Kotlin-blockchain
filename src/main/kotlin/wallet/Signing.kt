package wallet

import java.security.Key
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.util.*

private const val signAlgorithm = "SHA256withRSA"

fun String.sign(privateKey: PrivateKey, algorithm: String = signAlgorithm): ByteArray {
    val rsa = Signature.getInstance(algorithm)
    rsa.initSign(privateKey)
    rsa.update(this.toByteArray())
    return rsa.sign()
}

fun String.verifySignature(publicKey: PublicKey, signature: ByteArray, algorithm: String = signAlgorithm): Boolean {
    val rsa = Signature.getInstance(algorithm)
    rsa.initVerify(publicKey)
    rsa.update(this.toByteArray())
    return rsa.verify(signature)
}

fun Key.encodeToString(): String {
    return Base64.getEncoder().encodeToString(this.encoded)
}