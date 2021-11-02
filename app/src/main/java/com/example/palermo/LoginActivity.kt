package com.example.palermo

import android.content.Intent
import android.os.Bundle
import com.example.palermo.MainActivity
import com.example.palermo.R
import com.example.palermo.ui.BaseActivity
import com.github.salomonbrys.kodein.instance
import coomecipar.example.palermo.Credential
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {
    val credential: Credential by injector.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonLogin.setOnClickListener { login() }
    }

    override fun onStart() {
        if (credential.password?.isNotEmpty() == true) {
            gotoMain()
        }
        super.onStart()
        //
    }

    private fun gotoMain() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }


    fun login() {
        val username = editTextUsuario.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        gotoMain()
    }

}
