package mvpdemo.zgc.mvpdemokotlin.presenter.home


import android.app.Activity
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import mvpdemo.zgc.mvpdemokotlin.R
import mvpdemo.zgc.mvpdemokotlin.app.APP
import mvpdemo.zgc.mvpdemokotlin.model.GankDataModel
import mvpdemo.zgc.mvpdemokotlin.model.entity.GankData
import mvpdemo.zgc.mvpdemokotlin.presenter.base.BasePresenter
import mvpdemo.zgc.mvpdemokotlin.service.ApiService
import mvpdemo.zgc.mvpdemokotlin.ui.adapter.HomeAdapter2
import mvpdemo.zgc.mvpdemokotlin.ui.contract.HomeContract
import javax.inject.Inject

/**
 * Created by Nick on 2017/1/7
 */
class HomePresenter @Inject
constructor() : BasePresenter(), HomeContract.Presenter {
    private var view: HomeContract.View? = null

    @Inject
    @JvmField
    var apiService: ApiService? = null

    private var mPage = 1

    private var mHomeList: MutableList<GankData>? = null
    //    private HomeAdapter mHomeAdapter = null;
    private var mHomeAdapter: HomeAdapter2? = null

    private var mActivity: Activity? = null

    init {

        mHomeList = ArrayList();
        //        mHomeAdapter = new HomeAdapter(mHomeList, context);
        mHomeAdapter = HomeAdapter2(mHomeList as ArrayList<GankData>)


    }


    fun loadGankData(clean: Boolean) {
        if (view == null) {
            return
        }
        view!!.setAdapter(mHomeAdapter)
//        Flowable.zip<GankDataModel, GankDataModel, GankDataModel>(apiService!!.getPicList(APP.pagesize(), mPage),
//                apiService!!.getVideoList(APP.pagesize(), mPage),
//                BiFunction<GankDataModel, GankDataModel, GankDataModel> { picDataModel, videoDataModel -> this.createHomeData(picDataModel, videoDataModel) })
//                .map<List<GankData>> { gankDataModel -> gankDataModel.results }
//                .flatMap<out GankData>(Function<List<GankData>, Publisher<out out GankData>> { Flowable.fromIterable(it) })
//                .toSortedList { o1, o2 -> o2.publishedAt!!.compareTo(o1.publishedAt!!) }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ gankData ->
//                    if (clean) mHomeList!!.clear()
//                    mHomeList!!.addAll(gankData)
//                    mHomeAdapter!!.notifyDataSetChanged()
//                    view!!.refreshComplete()
//                    mHomeAdapter!!.loadMoreComplete()
//                }, Consumer<Throwable> { it.printStackTrace() })

        Flowable.zip<GankDataModel, GankDataModel, GankDataModel>(apiService!!.getPicList(APP.pagesize(), mPage),
                apiService!!.getVideoList(APP.pagesize(), mPage),
                BiFunction<GankDataModel, GankDataModel, GankDataModel> { picDataModel, videoDataModel -> this.createHomeData(picDataModel, videoDataModel) })
                .map<List<GankData>> { gankDataModel -> gankDataModel.results }
                .flatMapIterable { it }
                .toSortedList { o1, o2 -> o2.publishedAt!!.compareTo(o1.publishedAt!!) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ gankData ->
                    run {
                        if (clean) mHomeList!!.clear()
                        mHomeList!!.addAll(gankData)
                        mHomeAdapter!!.notifyDataSetChanged()
                        view!!.refreshComplete()
                        mHomeAdapter!!.loadMoreComplete()
                    }
                }, { throwable: Throwable? -> throwable!!.printStackTrace() })
//                .subscribe({ gankData ->
//                    run {
//                        if (clean) mHomeList!!.clear()
//                        mHomeList!!.addAll(gankData)
//                        mHomeAdapter!!.notifyDataSetChanged()
//                        view!!.refreshComplete()
//                        mHomeAdapter!!.loadMoreComplete()
//                    }
//                }, Consumer<Throwable> { it.printStackTrace() })
    }

    fun refresh() {
        mPage = 1
        loadGankData(true)
    }

    fun loadMore() {
        mPage++
        loadGankData(false)
    }

    private fun createHomeData(picDataModel: GankDataModel, videoDataModel: GankDataModel): GankDataModel {
        for (i in 0 until picDataModel.results!!.size) {
            val picData = picDataModel.results!![i]
            val videoData = videoDataModel.results!![i]

            picData.desc = picData.desc + " " + videoData.desc
        }
        return picDataModel
    }


    override fun initData() {
        //加载更多
        mHomeAdapter!!.setOnLoadMoreListener({ loadMore() }, view!!.getRecyclerView())

        mHomeAdapter!!.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.iv_pic -> Picasso.get().load(mHomeList!![position].url).fetch(object : Callback {

                    override fun onSuccess() {
                        startPhotoActivity(view, position)
                    }

                    override fun onError(e: Exception) {
                        e.printStackTrace()
                    }

                })
            }
        }
    }

    private fun startPhotoActivity(view: View, position: Int) {
        val compatOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view.findViewById(R.id.iv_pic), mActivity!!.getString(R.string.photo_transiton_tag))
        ARouter.getInstance()
                .build("/util/PhotoViewActivity")
                .withString(PhotoViewPresenter.PHOTO_URL, mHomeList!![position].url)
                .withOptionsCompat(compatOptions)
                .navigation(mActivity)
    }

    override fun setActivity(activity: Activity) {
        this.mActivity = activity
    }

    override fun takeView(view: HomeContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }
}

