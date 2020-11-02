package com.camilo.myifood.util

import org.eclipse.microprofile.jwt.Claims
import org.jose4j.jws.AlgorithmIdentifiers
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.NumericDate
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import java.util.stream.Collectors


/**
 * Utilities for generating a JWT for testing
 *
 * Peguei nesse endereço
 * https://quarkus.io/guides/security-jwt
 */
object TokenUtils {
    /**
     * Utility method to generate a JWT string from a JSON resource file that is signed by the privateKey.pem
     * test resource key, possibly with invalid fields.
     *
     * @param jsonResName - name of test resources file
     * @param timeClaims - used to return the exp, iat, auth_time claims
     * @return the JWT string
     * @throws Exception on parse failure
     */
    fun generateTokenString(jsonResName: String?, timeClaims: Map<String?, Long?>?): String {
        // Use the test private key associated with the test public key for a valid signature
        val pk = readPrivateKey("/privateKey.pem")
        return generateTokenString(pk, "/privateKey.pem", jsonResName, timeClaims)
    }

    fun generateTokenString(
        privateKey: PrivateKey?, kid: String?,
        jsonResName: String?, timeClaims: Map<String?, Long?>?
    ): String {
        val claims = JwtClaims.parse(readTokenContent(jsonResName))
        val currentTimeInSecs = currentTimeInSecs().toLong()
        val exp =
            if (timeClaims != null && timeClaims.containsKey(Claims.exp.name)) timeClaims[Claims.exp.name]!! else currentTimeInSecs + 300
        claims.issuedAt = NumericDate.fromSeconds(currentTimeInSecs)
        claims.setClaim(Claims.auth_time.name, NumericDate.fromSeconds(currentTimeInSecs))
        claims.expirationTime = NumericDate.fromSeconds(exp)
        for ((key, value) in claims.claimsMap) {
            System.out.printf("\tAdded claim: %s, value: %s\n", key, value)
        }
        val jws = JsonWebSignature()
        jws.payload = claims.toJson()
        jws.key = privateKey
        jws.keyIdHeaderValue = kid
        jws.setHeader("typ", "JWT")
        jws.algorithmHeaderValue = AlgorithmIdentifiers.RSA_USING_SHA256
        return jws.compactSerialization
    }

    private fun readTokenContent(jsonResName: String?): String {
        val contentIS = TokenUtils::class.java.getResourceAsStream(jsonResName)
        BufferedReader(InputStreamReader(contentIS)).use { buffer ->
            return buffer.lines().collect(Collectors.joining("\n"))
        }
    }

    /**
     * Read a PEM encoded private key from the classpath
     *
     * @param pemResName - key file resource name
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    fun readPrivateKey(pemResName: String?): PrivateKey {
        val contentIS = TokenUtils::class.java.getResourceAsStream(pemResName)
        val tmp = ByteArray(4096)
        val length = contentIS.read(tmp)
        return decodePrivateKey(String(tmp, 0, length, Charset.defaultCharset()))
    }

    /**
     * Decode a PEM encoded private key string to an RSA PrivateKey
     *
     * @param pemEncoded - PEM string for private key
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    fun decodePrivateKey(pemEncoded: String): PrivateKey {
        val encodedBytes = toEncodedBytes(pemEncoded)
        val keySpec = PKCS8EncodedKeySpec(encodedBytes)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePrivate(keySpec)
    }

    private fun toEncodedBytes(pemEncoded: String): ByteArray {
        val normalizedPem = removeBeginEnd(pemEncoded)
        return Base64.getDecoder().decode(normalizedPem)
    }

    private fun removeBeginEnd(pem: String): String {
        var pem = pem
        pem = pem.replace("-----BEGIN (.*)-----".toRegex(), "")
        pem = pem.replace("-----END (.*)----".toRegex(), "")
        pem = pem.replace("\r\n".toRegex(), "")
        pem = pem.replace("\n".toRegex(), "")
        return pem.trim { it <= ' ' }
    }

    /**
     * @return the current time in seconds since epoch
     */
    fun currentTimeInSecs(): Int {
        val currentTimeMS = System.currentTimeMillis()
        return (currentTimeMS / 1000).toInt()
    }
}