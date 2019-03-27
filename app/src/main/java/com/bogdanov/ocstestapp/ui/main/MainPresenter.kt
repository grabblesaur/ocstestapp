package com.bogdanov.ocstestapp.ui.main

import android.util.Log
import com.bogdanov.ocstestapp.base.AbstractBasePresenter
import com.bogdanov.ocstestapp.data.api.model.Vacancy
import com.bogdanov.ocstestapp.di.activity.ActivityScope
import com.bogdanov.ocstestapp.domain.MainInteractor
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class MainPresenter @Inject constructor(val mainInteractor: MainInteractor) : AbstractBasePresenter<MainView>() {

    private val TAG = MainPresenter::class.java.simpleName

    fun getData() {
        getView()?.setProgressBar(true)
        mainInteractor.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<ArrayList<Vacancy>> {
                private var disposable: Disposable? = null

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onSuccess(t: ArrayList<Vacancy>) {
                    Log.i(TAG, "onSuccess: ${t.size}")
                    getView()?.showCandidates(t)
                    getView()?.setProgressBar(false)
                    disposeCheck(disposable)
                }


                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: $e")
                    getView()?.onError(e)
                    getView()?.setProgressBar(false)
                    disposeCheck(disposable)
                }
            })
    }


}