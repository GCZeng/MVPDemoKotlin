package mvpdemo.zgc.mvpdemokotlin.app

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import mvpdemo.zgc.mvpdemokotlin.BuildConfig
import mvpdemo.zgc.mvpdemokotlin.R
import mvpdemo.zgc.mvpdemokotlin.di.component.DaggerAppComponent
import mvpdemo.zgc.mvpdemokotlin.util.DisplayUtiil
import mvpdemo.zgc.mvpdemokotlin.util.LogUtil
import mvpdemo.zgc.mvpdemokotlin.util.image.ImageLoader

/**
 *Author: zgc
 *Time: 2018/3/30 下午7:20
 *Description: APP
 **/
class APP : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    companion object {
        private var instance: Application? = null
        private var pagesize: Int? = 20
        fun instance() = instance!!
        fun pagesize() = pagesize!!
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        LogUtil.init(BuildConfig.LOG_DEBUG)
        DisplayUtiil.init()

        ImageLoader.init(R.mipmap.pic_placeholder)

        //Arouter
        if (BuildConfig.LOG_DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }
}