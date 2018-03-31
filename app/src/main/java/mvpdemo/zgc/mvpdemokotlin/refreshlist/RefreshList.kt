package mvpdemo.zgc.mvpdemokotlin.refreshlist

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet

/**
 * Created by Nick on 2017/2/5
 */
class RefreshList : SwipeRefreshLayout, RefreshInterface {
    override fun refresh(listener: () -> Unit) {
//        setOnRefreshListener{listener.invoke()}
    }


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun refresh(refresh: Refresh) {
        if (refresh != null) {
            setOnRefreshListener { refresh.refreh() }
        }
    }


    override fun refreshComplete() {
        isRefreshing = false
    }

    interface Refresh {
        fun refreh()
    }

}
