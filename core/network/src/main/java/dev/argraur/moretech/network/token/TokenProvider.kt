package dev.argraur.moretech.network.token

import javax.inject.Inject

class TokenProvider @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    fun getToken() : String? {
        return tokenStorage.getToken()
    }
}