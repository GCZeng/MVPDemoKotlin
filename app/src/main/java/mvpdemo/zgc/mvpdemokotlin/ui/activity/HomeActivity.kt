package mvpdemo.zgc.mvpdemokotlin.ui.activity

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager.GAP_HANDLING_NONE
import android.util.TypedValue
import mvpdemo.zgc.mvpdemokotlin.R
import mvpdemo.zgc.mvpdemokotlin.presenter.home.HomePresenter
import mvpdemo.zgc.mvpdemokotlin.refreshlist.RefreshList
import mvpdemo.zgc.mvpdemokotlin.ui.activity.base.BaseDiActivity
import mvpdemo.zgc.mvpdemokotlin.ui.adapter.HomeAdapter2
import mvpdemo.zgc.mvpdemokotlin.ui.adapter.decoration.HomeItemDecoration
import mvpdemo.zgc.mvpdemokotlin.ui.contract.HomeContract
import javax.inject.Inject

/**
 * Created by Nick on 2017/12/1
 */
class HomeActivity : BaseDiActivity(), HomeContract.View {
    private var rl_list: RefreshList? = null

    private var refreshView: RecyclerView? = null

    @Inject
    @JvmField
    var mHomePresenter: HomePresenter? = null


    override fun provideContentViewId(): Int {
        return R.layout.activity_home
    }

    override fun getRecyclerView(): RecyclerView? {
        return refreshView
    }

    override fun initView() {

        rl_list = findViewById(R.id.rl_list);
        refreshView = findViewById(R.id.rv_list)

        setTitle(R.string.app_name)

        rl_list!!.refresh({ mHomePresenter!!.refresh() })

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        rl_list!!.setProgressViewOffset(false, 0, TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, resources
                        .displayMetrics).toInt())

        //设置布局管理器
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = GAP_HANDLING_NONE
        refreshView!!.layoutManager = layoutManager

        refreshView!!.addItemDecoration(HomeItemDecoration(this))

        refreshView!!.setPadding(0, 0, 0, 0)
        refreshView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                layoutManager.invalidateSpanAssignments()
            }
        })

        //设置adapter
        refreshView!!.setHasFixedSize(true)

        //间隔
        //        rv_list.addItemDecoration(new HomeItemDecoration(this));

        mHomePresenter!!.setActivity(this)
    }

    override fun initData() {
        rl_list!!.isRefreshing = true
    }

    override fun setAdapter(adapter: HomeAdapter2?) {
        if (refreshView!!.adapter !== adapter) {
            refreshView!!.adapter = adapter
        }
    }

    override fun refreshComplete() {
        rl_list!!.refreshComplete()
    }

    override fun onResume() {
        super.onResume()
        mHomePresenter!!.takeView(this)
        if (isFirstLoadData) {
            isFirstLoadData = false
            mHomePresenter!!.initData()
            mHomePresenter!!.loadGankData(true)
        }
    }

    override fun onPause() {
        super.onPause()
        mHomePresenter!!.dropView()
    }
}

