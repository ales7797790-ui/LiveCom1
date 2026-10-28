package com.livecom.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.livecom.app.core.util.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val auth: FirebaseAuth) {

    val currentUid: String? get() = auth.currentUser?.uid

    suspend fun ensureSignedIn(): Result<String> = try {
        val user = auth.currentUser ?: auth.signInAnonymously().await().user
        if (user != null) Result.Success(user.uid)
        else Result.Error("فشل تسجيل الدخول")
    } catch (e: Exception) {
        Result.Error(e.localizedMessage ?: "خطأ غير معروف", e)
    }
}
