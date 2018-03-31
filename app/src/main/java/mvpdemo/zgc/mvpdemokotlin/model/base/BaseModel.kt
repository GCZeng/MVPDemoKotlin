package mvpdemo.zgc.mvpdemokotlin.model.base

/**
 * Base Model
 * Created by Nick on 2017/1/7
 */
open class BaseModel<T> {
    var isError: Boolean = false
    var results: T? = null
}
