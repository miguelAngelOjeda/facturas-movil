package com.example.palermo.utils


import com.example.palermo.network.ApiRequest
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxUtils {
    fun <T> onBackground(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> basicApi(): ObservableTransformer<in ApiRequest<T>, out List<T>>? {
        return ObservableTransformer { upstream ->
            upstream
                    .compose(onBackground())
                    .flatMap { Observable.just(it.rows) }
        }
    }

}