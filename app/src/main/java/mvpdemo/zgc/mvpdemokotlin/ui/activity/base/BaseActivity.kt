package mvpdemo.zgc.mvpdemokotlin.ui.activity.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

import butterknife.ButterKnife
import dagger.android.support.DaggerAppCompatActivity
import mvpdemo.zgc.mvpdemokotlin.R

/**
 * Created by Nick on 2017/12/1
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    protected var mToolbar: Toolbar? = null

    private var mBackListener: BackListener? = null

    protected var isFirstLoadData = true//用于判断页面是否加载过一次数据

    protected abstract fun provideContentViewId(): Int

    protected abstract fun initView()

    protected abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (provideContentViewId() > 0) {
            setContentView(provideContentViewId())
        }

        mToolbar = findViewById(R.id.toolbar)
        if (mToolbar != null) {
            setSupportActionBar(mToolbar)
        }

//        ButterKnife.bind(this)
        initView()
        initData()
    }

    protected fun showBackPress(backListener: BackListener) {
        this.mBackListener = backListener
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mToolbar!!.setNavigationOnClickListener { v ->
            if (mBackListener != null) {
                mBackListener!!.click()
            }
        }
    }

    override fun setTitle(resTitle: Int) {
        if (supportActionBar != null) {
            supportActionBar!!.setTitle(resTitle)
        }
    }

    protected fun setTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    @JvmOverloads
    protected fun goAct(clazz: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(this, clazz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> if (mBackListener != null) {
                mBackListener!!.click()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    interface BackListener {
        fun click()
    }

    protected fun hideTitle() {
        mToolbar!!.animate()
                .translationY((-mToolbar!!.height).toFloat()).interpolator = AccelerateDecelerateInterpolator()
    }

    protected fun showTitle() {
        mToolbar!!.animate()
                .translationY(0f).interpolator = AccelerateDecelerateInterpolator()
    }

    protected fun setAppBarAlpha(alpha: Float) {
        mToolbar!!.alpha = alpha
    }

    protected fun hideToolbar() {
        mToolbar!!.visibility = View.GONE
    }
}
