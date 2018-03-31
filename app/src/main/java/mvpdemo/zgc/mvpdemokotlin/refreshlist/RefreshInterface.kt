package mvpdemo.zgc.mvpdemokotlin.refreshlist

/**
 * Created by Nick on 2017/2/5
 */
interface RefreshInterface {
    /**
     * 刷新
     */
    fun refresh(refresh: RefreshList.Refresh)

    fun refresh(listener: () -> Unit)

    /**
     * 刷新完成
     */
    fun refreshComplete()
}
