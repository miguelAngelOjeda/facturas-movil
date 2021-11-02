package com.example.palermo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import io.reactivex.disposables.CompositeDisposable


open class BaseFragment : Fragment(), KodeinInjected {
    val disposables = CompositeDisposable()
    override val injector = KodeinInjector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(appKodein())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}