package dev.argraur.moretech.network.token

class TokenStorage {
    private var _token: String? = null

    fun setToken(token: String) {
        _token = token
    }

    fun getToken() = _token
}