package mvpdemo.zgc.mvpdemokotlin.ui.adapter.base

import android.support.v7.widget.RecyclerView


/**
 * Created by Nick on 2017/2/6
 */
abstract class BaseAdapter<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {
    protected var data: List<*>? = null


    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    protected fun getData(position: Int): Any? {
        return if (data == null) null else data!![position]
    }
}
