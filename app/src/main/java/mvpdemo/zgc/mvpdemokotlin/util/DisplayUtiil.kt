package mvpdemo.zgc.mvpdemokotlin.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import mvpdemo.zgc.mvpdemokotlin.app.APP


/**
 * Created by Nick on 2017/12/5
 */
object DisplayUtiil {
    var screenWidth = 0
        private set
    var screenHeight = 0
        private set

    fun init() {
        val metrics = DisplayMetrics()
        val wm = APP.instance().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        screenWidth = metrics.widthPixels
        screenHeight = metrics.heightPixels
    }


}
