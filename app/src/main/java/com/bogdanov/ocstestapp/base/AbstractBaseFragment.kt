package com.bogdanov.ocstestapp.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bogdanov.ocstestapp.R
import dagger.android.support.AndroidSupportInjection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

abstract class AbstractBaseFragment<in V : BaseView, P : BasePresenter<V>> : DialogFragment(), BaseView {
    @Inject
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this as V)
    }

    override fun onDetach() {
        presenter.detachFromView()
        super.onDetach()
    }

    override fun onError(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(throwable: Throwable?) {
        if (throwable != null) {
            val message =
                when (throwable) {
                    is UnknownHostException, is TimeoutException, is SocketTimeoutException -> {
                        getString(R.string.no_connection)
                    }
                    else -> getString(R.string.unknown_error)
                }
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }
}