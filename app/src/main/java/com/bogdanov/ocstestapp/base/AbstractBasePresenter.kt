package com.bogdanov.ocstestapp.base

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

abstract class AbstractBasePresenter<T : BaseView> : BasePresenter<T> {

    private var views: ArrayList<WeakReference<T>>? = null
    private var view: WeakReference<T>? = null
    private val disposables = CompositeDisposable()

    override fun takeView(t: T) {
//        view = WeakReference(t)
        if (views == null) {
            views = ArrayList()
        }
        val view = WeakReference(t)
        views!!.add(view)
    }

    override fun detachFromView() {
        disposables.clear()
//        view = null
        views = null
    }

    fun getView() = views?.get(0)?.get()

    fun getViews(): ArrayList<T> {
        val tList = ArrayList<T>()
        if(views != null) {
            for (view in views!!.iterator()) {
                val t = view.get()
                tList.add(t!!)
            }
        }
        return tList
    }

    fun <T> subscribe(observable: Single<T>, action: ((T) -> Unit)) {
        disposables.add(observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ action.invoke(it) }, {
                getView()?.onError(it.localizedMessage)
            })
        )
    }

    fun <T> subscribe(observable: Maybe<T>, action: ((T) -> Unit)) {
        disposables.add(observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ action.invoke(it) }, {
                getView()?.onError(it.localizedMessage)
            })
        )
    }

    fun <T> subscribe(observable: Flowable<T>, action: ((T) -> Unit)) {
        disposables.add(observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ action.invoke(it) }, {
                getView()?.onError(it.localizedMessage)
            })
        )
    }

    fun subscribe(observable: Completable, action: (() -> Unit)) {
        disposables.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ action.invoke() },
                    { })
        )
    }

    fun subscribe(observable: Completable, action: (() -> Unit), error: ((Throwable) -> Unit)) {
        disposables.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ action.invoke() },
                    {
                        error.invoke(it)
                    })
        )
    }

    fun <T> subscribe(observable: Single<T>, action: ((T) -> Unit), error: ((Throwable) -> Unit)) {
        disposables.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ action.invoke(it) },
                    {
                        error.invoke(it)
                    })
        )
    }

    fun disposeCheck(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed)
            disposable.dispose()
    }
}
