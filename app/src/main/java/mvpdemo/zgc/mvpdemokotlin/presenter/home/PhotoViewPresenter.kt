package mvpdemo.zgc.mvpdemokotlin.presenter.home

import android.app.Activity

import com.squareup.picasso.Picasso

import javax.inject.Inject

import mvpdemo.zgc.mvpdemokotlin.presenter.base.BasePresenter
import mvpdemo.zgc.mvpdemokotlin.ui.contract.PhotoViewContract
import mvpdemo.zgc.mvpdemokotlin.util.LogUtil


/**
 * Created by Nick on 2017/12/7
 */
class PhotoViewPresenter @Inject
constructor() : BasePresenter(), PhotoViewContract.Presenter {
    private var view: PhotoViewContract.View? = null


    //TODO 这里使用Glide加载图片的时候，第一次进来会跳一下，应该是图片显示策略问题，暂时先改用Picasso解决
    override fun showPhoto(activity: Activity, url: String) {
        LogUtil.d(url)
        Picasso.get().load(url).into(view!!.photoView)
    }

    override fun takeView(view: PhotoViewContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    companion object {

        var PHOTO_URL = "photo_url"
    }
}
