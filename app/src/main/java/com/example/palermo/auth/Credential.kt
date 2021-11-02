package coomecipar.example.palermo

import com.google.gson.Gson
import coomecipar.example.palermo.models.auth.LoginResponse
import coomecipar.example.palermo.models.auth.Usuario


import devliving.online.securedpreferencestore.SecuredPreferenceStore
import java.util.*

class Credential() {
    val prefStore by lazy { SecuredPreferenceStore.getSharedInstance() }
    private var expiresAt = Date()

    init {
        refreshExpiresAt()
    }

    private fun refreshExpiresAt() {
        expiresAt = Calendar.getInstance().apply {
            add(Calendar.SECOND, expiresIn)
        }.time
    }

    fun logout() {
        this.username = ""
        this.password = ""
    }

    fun persistUsuario(response: LoginResponse, password: String? = null) {
        val (usuario, token) = response
        this.username = usuario.username
        this.idMovil = usuario.idMovil
        this.loggedUser = usuario
        if (password != null) {
            this.password = password
        }
    }

    private var loggedUser: Usuario? = null

    val usuario: Usuario?
        get() = this.loggedUser


    var idMovil: String?
        get() = prefStore.getString("idMovil", "")
        set(value) {
            prefStore.edit().putString("idMovil", value).apply()
        }

    var expiresIn: Int
        get() = prefStore.getInt("expiresIn", 0)
        set(value) {
            prefStore.edit().putInt("expiresIn", value).apply()
        }

    var username: String?
        get() = prefStore.getString("username", "")
        set(value) {
            prefStore.edit().putString("username", value).apply()
        }

    var password: String?
        get() = prefStore.getString("password", "")
        set(value) {
            prefStore.edit().putString("password", value).apply()
        }

    val isExpired: Boolean
        get() = expiresAt < Calendar.getInstance().time
}