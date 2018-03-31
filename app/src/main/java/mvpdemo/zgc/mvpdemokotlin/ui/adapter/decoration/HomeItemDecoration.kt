package mvpdemo.zgc.mvpdemokotlin.ui.adapter.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.View

import mvpdemo.zgc.mvpdemokotlin.R


/**
 * Created by Nick on 2017/2/7
 */
class HomeItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    //    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private val mDivider: Drawable

    init {
        //        final TypedArray array = context.obtainStyledAttributes(ATTRS);
        //        mDivider = array.getDrawable(0);
        mDivider = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            context.resources.getDrawable(R.drawable.recyclerview_item_decoration_default, null)
        else
            context.resources.getDrawable(R.drawable.recyclerview_item_decoration_default)
        //        array.recycle();
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    // 水平线
    fun drawHorizontal(c: Canvas, parent: RecyclerView) {

        val childCount = parent.childCount

        // 在每一个子控件的底部画线
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val left = child.left + child.paddingLeft
            val right = child.width + child.left - child.paddingRight
            //            final int top = child.getBottom() - mDivider.getIntrinsicHeight() - child.getPaddingBottom();
            //            final int bottom = top + mDivider.getIntrinsicHeight();

            val top = child.bottom - child.paddingBottom
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    // 竖直线
    fun drawVertical(c: Canvas, parent: RecyclerView) {

        val childCount = parent.childCount

        // 在每一个子控件的右侧画线
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val right = child.right - child.paddingRight
            val left = right - mDivider.intrinsicWidth
            val top = child.top + child.paddingTop
            val bottom = child.top + child.height - child.paddingBottom

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)

        }
    }

    // Item之间的留白
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        outRect.set(0, 0, 0, 0)
    }
}
