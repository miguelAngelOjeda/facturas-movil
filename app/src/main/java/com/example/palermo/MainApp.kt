package com.example.palermo

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.example.palermo.ui.BaseFragment
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.github.salomonbrys.kodein.lazy
import coomecipar.example.palermo.di.ApplicationModule
import coomecipar.example.palermo.di.networkModule
import devliving.online.securedpreferencestore.DefaultRecoveryHandler
import devliving.online.securedpreferencestore.SecuredPreferenceStore
import kotlinx.android.synthetic.main.activity_main.*



class MainApp : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(autoAndroidModule(this@MainApp))
        import(ApplicationModule(this@MainApp))
        import(networkModule)
    }

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        initEncryption()
    }

    private fun initEncryption() {
        val seedKey = "AOJSDFSISODFIDSHFIOHSD".toByteArray()
        SecuredPreferenceStore.init(applicationContext, null, null, seedKey, DefaultRecoveryHandler())
    }

}