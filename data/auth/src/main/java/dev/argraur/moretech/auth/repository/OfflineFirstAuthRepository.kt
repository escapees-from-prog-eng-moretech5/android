package dev.argraur.moretech.auth.repository

import dev.argraur.moretech.auth.model.AuthData
import dev.argraur.moretech.auth.model.toRegular
import dev.argraur.moretech.database.dao.AuthDao
import dev.argraur.moretech.database.model.AuthDataEntity
import dev.argraur.moretech.network.AuthNetworkDataSource
import dev.argraur.moretech.network.model.LoginRequest
import dev.argraur.moretech.network.retrofit.AuthService
import dev.argraur.moretech.network.token.TokenStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class OfflineFirstAuthRepository @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authDao: AuthDao,
    private val authNetworkDataSource: AuthNetworkDataSource
) : AuthRepository {
    init {
        refreshToken()
    }

    override suspend fun getAuthData(): List<AuthData> {
        return authDao.loadAuthData().map { it.toRegular() }
    }

    override suspend fun login(phoneNumber: String, password: String): Boolean {
        val result = authNetworkDataSource.login(phoneNumber, password)
        if (result.token != "") {
            authDao.insert(authDataEntity = AuthDataEntity(0, phoneNumber, result.token))
            refreshToken()
            return true
        }
        return false
    }

    override suspend fun register(phoneNumber: String, password: String): Boolean {
        val result = authNetworkDataSource.register(phoneNumber, password)
        if (result.token != "") {
            authDao.insert(authDataEntity = AuthDataEntity(0, phoneNumber, result.token))
            refreshToken()
            return true
        }
        return false
    }

    private fun refreshToken() {
        runBlocking(Dispatchers.IO) {
            try {
                val authDataList = getAuthData()
                val token = authDataList.first().token
                tokenStorage.setToken(token)
            } catch (_: NoSuchElementException) {
            }
        }
    }
}