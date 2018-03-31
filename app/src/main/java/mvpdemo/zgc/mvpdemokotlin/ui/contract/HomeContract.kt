package mvpdemo.zgc.mvpdemokotlin.ui.contract

import android.app.Activity
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter

import mvpdemo.zgc.mvpdemokotlin.presenter.base.IBasePresenter
import mvpdemo.zgc.mvpdemokotlin.ui.adapter.HomeAdapter2
import mvpdemo.zgc.mvpdemokotlin.ui.contract.base.IBaseView


/**
 * Created by Nick on 2017/1/7
 */
interface HomeContract {
    interface View : IBaseView<Presenter> {

//        val refreshView: RecyclerView

        /**
         * 设置适配器
         *
         * @param adapter
         */
        fun setAdapter(adapter: HomeAdapter2?)

        /**
         * 刷新完成
         */
        fun refreshComplete()

        fun getRecyclerView(): RecyclerView?

    }

    interface Presenter : IBasePresenter<View> {
        fun initData()

        fun setActivity(activity: Activity)
    }

}
