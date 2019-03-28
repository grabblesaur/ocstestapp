package com.bogdanov.ocstestapp.ui.main

import android.util.Log
import com.bogdanov.ocstestapp.base.AbstractBasePresenter
import com.bogdanov.ocstestapp.di.activity.ActivityScope
import com.bogdanov.ocstestapp.domain.MainInteractor
import com.bogdanov.ocstestapp.domain.Vacancy
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class MainPresenter @Inject constructor(val mainInteractor: MainInteractor) : AbstractBasePresenter<MainView>() {

    private val TAG = MainPresenter::class.java.simpleName

    fun getData(query: String?, page: Int) {
        Log.i(TAG, "getData: query = $query page = $page")
        mainInteractor.getData(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<MutableList<Vacancy>> {
                private var disposable: Disposable? = null

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onComplete() {
                    disposeCheck(disposable)
                }

                override fun onNext(t: MutableList<Vacancy>) {
                    Log.i(TAG, "onNext: ${t.size}")
                    getView()?.showCandidates(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: $e")
                    getView()?.onError(e)
                    disposeCheck(disposable)
                }
            })
    }


}