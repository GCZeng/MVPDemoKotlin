package mvpdemo.zgc.mvpdemokotlin.ui.common

import android.net.Uri

import com.alibaba.android.arouter.launcher.ARouter

import mvpdemo.zgc.mvpdemokotlin.ui.activity.base.BaseActivity


/**
 * Author: zgc
 * Time: 2018/3/29 下午3:34
 * Description: SchameFilterActivity
 */
class SchameFilterActivity : BaseActivity() {
    override fun provideContentViewId(): Int {
        return 0
    }

    override fun initView() {
        val uri = intent.data
        ARouter.getInstance().build(uri!!).navigation()
        finish()
    }

    override fun initData() {

    }
}
