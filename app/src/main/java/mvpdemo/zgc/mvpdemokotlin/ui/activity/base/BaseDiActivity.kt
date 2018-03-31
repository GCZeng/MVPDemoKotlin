package mvpdemo.zgc.mvpdemokotlin.ui.activity.base

import android.os.Bundle

import dagger.android.AndroidInjection

/**
 * Author: zgc
 * Time: 2018/3/28 下午10:42
 * Description: BaseDiActivity
 */

abstract class BaseDiActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}
