package mvpdemo.zgc.mvpdemokotlin.presenter.base

/**
 * Base Presenter
 * Created by Nick on 2017/1/6
 */
interface IBasePresenter<T> {

    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    fun takeView(view: T)

    /**
     * Drops the reference to the view when destroyed
     */
    fun dropView()

}
