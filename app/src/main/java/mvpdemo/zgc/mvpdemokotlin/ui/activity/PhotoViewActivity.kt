package mvpdemo.zgc.mvpdemokotlin.ui.activity


import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.ActionBar
import android.widget.ImageView

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.github.chrisbanes.photoview.PhotoView

import javax.inject.Inject

import butterknife.BindView
import kotterknife.bindView
import mvpdemo.zgc.mvpdemokotlin.R
import mvpdemo.zgc.mvpdemokotlin.presenter.home.PhotoViewPresenter
import mvpdemo.zgc.mvpdemokotlin.ui.activity.base.BaseActivity
import mvpdemo.zgc.mvpdemokotlin.ui.activity.base.BaseDiActivity
import mvpdemo.zgc.mvpdemokotlin.ui.contract.PhotoViewContract

/**
 * Created by Nick on 2017/12/7
 */
@Route(path = "/util/PhotoViewActivity")
class PhotoViewActivity : BaseDiActivity(), PhotoViewContract.View {

    private var pv_pic: PhotoView? = null

    @Inject
    @JvmField
    var mPhotoViewPresenter: PhotoViewPresenter? = null

    @Autowired
    @JvmField
    public var photo_url: String? = null

    override val photoView: PhotoView?
        get() = pv_pic

    override fun provideContentViewId(): Int {
        return R.layout.activity_photo_view
    }

    override fun initView() {

        pv_pic = findViewById(R.id.pv_pic)

        ARouter.getInstance().inject(this)


        showBackPress(object : BaseActivity.BackListener {
            override fun click() {
                finish()
            }
        })

        pv_pic!!.setOnPhotoTapListener { view, x, y ->
            val actionBar = supportActionBar
            if (actionBar!!.isShowing) {
                actionBar.hide()
            } else {
                actionBar.show()
            }
        }
    }

    override fun initData() {
        setTitle(R.string.photo_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewCompat.setTransitionName(pv_pic, getString(R.string.photo_transiton_tag))
        //        setAppBarAlpha(0.7f);
    }

    override fun onResume() {
        super.onResume()
        mPhotoViewPresenter!!.takeView(this)
        if (isFirstLoadData) {
            isFirstLoadData = false

            mPhotoViewPresenter!!.showPhoto(this, photo_url!!)

        }
    }

    override fun onPause() {
        super.onPause()
        mPhotoViewPresenter!!.dropView()
    }
}
