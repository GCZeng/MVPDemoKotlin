package mvpdemo.zgc.mvpdemokotlin.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import mvpdemo.zgc.mvpdemokotlin.R
import mvpdemo.zgc.mvpdemokotlin.model.entity.GankData
import mvpdemo.zgc.mvpdemokotlin.util.image.ImageLoader
import mvpdemo.zgc.mvpdemokotlin.util.image.ImageManager
import java.util.*


/**
 * Created by Nick on 2017/2/6
 */
class HomeAdapter2(data: List<GankData>) : BaseQuickAdapter<GankData, BaseViewHolder>(R.layout.rv_home_item, data) {

    private val mIHeightMap = HashMap<String?, Int>()

    override fun convert(helper: BaseViewHolder, gankData: GankData) {
        helper.setText(R.id.tv_title, gankData.desc)
        val imageView = helper.getView<ImageView>(R.id.iv_pic)

        var imgHeight:Int? = 0
        if (mIHeightMap.containsKey(gankData.url)) {
            imgHeight = mIHeightMap.get(gankData.url)
        } else {
            imgHeight = (300 + Math.random() * 300).toInt()
            mIHeightMap[gankData.url] = imgHeight
        }

        val lp = imageView.layoutParams
        lp.height = imgHeight!!
        imageView.layoutParams = lp

        ImageLoader.with(mContext)
                .url(gankData.url)
                .scaleType(ImageManager.ScaleType.CENTER_CROP)
                .into(imageView)

        helper.addOnClickListener(R.id.iv_pic)

    }


}
