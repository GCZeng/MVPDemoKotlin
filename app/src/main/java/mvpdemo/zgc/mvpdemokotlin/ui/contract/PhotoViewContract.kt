package mvpdemo.zgc.mvpdemokotlin.ui.contract

import android.app.Activity
import android.widget.ImageView
import com.github.chrisbanes.photoview.PhotoView

import mvpdemo.zgc.mvpdemokotlin.presenter.base.IBasePresenter
import mvpdemo.zgc.mvpdemokotlin.ui.contract.base.IBaseView


/**
 * Created by Nick on 2017/1/7
 */
interface PhotoViewContract {
    interface View : IBaseView<Presenter> {
        val photoView: PhotoView?
    }

    interface Presenter : IBasePresenter<View> {
        fun showPhoto(activity: Activity, url: String)
    }

}
