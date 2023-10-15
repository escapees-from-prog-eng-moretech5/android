package dev.argraur.moretech.auth.repository

import android.util.Log
import dev.argraur.moretech.auth.model.AuthData
import dev.argraur.moretech.auth.model.toRegular
import dev.argraur.moretech.database.dao.AuthDao
import dev.argraur.moretech.network.AuthNetworkDataSource
import dev.argraur.moretech.network.token.TokenStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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
        try {
            val result = authNetworkDataSource.login(phoneNumber, password)
            if (result.token != "") {
                withContext(Dispatchers.IO) {
                    authDao.insert(authDataEntity = result.toRegular().toEntity())
                }
                refreshToken()
                return true
            }
        } catch (e: Exception) {
            Log.e(this::class.simpleName!!, "Failed to login due to network exception")
        }
        return false
    }

    override suspend fun register(phoneNumber: String, password: String, name: String): Boolean {
        Log.i("AuthRepository", "Registering...")
        try {
            val result = authNetworkDataSource.register(phoneNumber, password, name)
            Log.i("AuthRepository", "result: ${result}")
            if (result.token != "") {
                withContext(Dispatchers.IO) {
                    authDao.insert(authDataEntity = result.toRegular().toEntity())
                }
                refreshToken()
                return true
            }
        } catch (e: Exception) {
            Log.e(this::class.simpleName!!, "Failed to login due to network exception: ${e.message}")
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