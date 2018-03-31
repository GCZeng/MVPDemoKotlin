package mvpdemo.zgc.mvpdemokotlin.util

import com.google.gson.Gson

/**
 * Created by Nick on 2017/12/10
 */
object JSONUtil {
    private var mGson: Gson? = null

    init {
        mGson = Gson()
    }

    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return mGson!!.fromJson(json, clazz)
    }

    fun toJson(`object`: Any): String {
        return mGson!!.toJson(`object`)
    }

}
