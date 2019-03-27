package com.bogdanov.ocstestapp.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import javax.inject.Inject
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.bogdanov.ocstestapp.R


abstract class AbstractBaseActivity<in V : BaseView, P : BasePresenter<V>> : AppCompatActivity(),
    HasSupportFragmentInjector, BaseView {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var presenter: P

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        presenter.takeView(this as V)
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
    }

    override fun onDestroy() {
        presenter.detachFromView()
        super.onDestroy()
    }

    override fun onError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = ""
    }

    fun setToolbarWithBackButton(toolbar: Toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
        toolbar.setNavigationOnClickListener { v -> onBackPressed() }
    }

    fun hideSoftKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}
