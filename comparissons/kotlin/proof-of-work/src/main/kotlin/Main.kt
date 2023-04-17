import org.apache.commons.codec.digest.DigestUtils.sha256Hex

var nonce = 0
const val data = "Hello, world!"

fun main() {
    mine(5, "e7wM2iSaaNssTJcHZRGaZYTEoSibEm6sqDdNm")
}

fun mine(prefix: Int, genesisHash: String) {
    val millis = System.currentTimeMillis()
    val prefixString = "0".repeat(prefix)
    var hashString: String

    do {
        hashString = calculateHash(genesisHash, millis)
        nonce++
    } while (hashString.substring(0, prefix) != prefixString)

    println("Mined Block Hash: $hashString with (nonce: $nonce)")
}

private fun calculateHash(genesisHash: String, millis: Long): String {
    val dataToHash = genesisHash + millis + nonce + data
    return sha256Hex(dataToHash)
}
