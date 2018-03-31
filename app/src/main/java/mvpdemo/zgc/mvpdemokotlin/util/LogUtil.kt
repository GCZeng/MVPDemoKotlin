package mvpdemo.zgc.mvpdemokotlin.util

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * 日志工具类
 * Created by Nick on 2017/1/10
 */
object LogUtil {
    private val TAG = "mvplog"//日志TAG
    private var isDebug = true

    fun init(isDebug: Boolean) {
        LogUtil.isDebug = isDebug
        init()
    }

    fun init() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
                //                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return isDebug
            }
        })
    }

    fun d(msg: String) {
        Logger.d(msg)
    }

    fun d(tag: String, msg: String) {
        Logger.t(tag).d(msg)
    }

    fun e(msg: String) {
        Logger.e(msg)
    }

    fun e(tag: String, msg: String) {
        Logger.t(tag).e(msg)
    }

    fun w(msg: String) {
        Logger.w(msg)
    }

    fun w(tag: String, msg: String) {
        Logger.t(tag).w(msg)
    }

    fun v(msg: String) {
        Logger.v(msg)
    }

    fun v(tag: String, msg: String) {
        Logger.t(tag).v(msg)
    }

    fun wtf(msg: String) {
        Logger.wtf(msg)
    }

    fun wtf(tag: String, msg: String) {
        Logger.t(tag).wtf(msg)
    }

    fun json(json: String) {
        Logger.json(json)
    }

    fun json(`object`: Any) {
        var json: String
        try {
            json = JSONUtil.toJson(`object`)
        } catch (e: Exception) {
            json = `object`.toString()
            e.printStackTrace()
        }

        Logger.json(json)
    }

    fun json(tag: String, msg: String) {
        Logger.t(tag).json(msg)
    }

    fun xml(xml: String) {
        Logger.xml(xml)
    }

    fun xml(tag: String, msg: String) {
        Logger.t(tag).xml(msg)
    }

    fun log(priority: Int, tag: String, msg: String, throwable: Throwable) {
        Logger.log(priority, tag, msg, throwable)
    }


}
