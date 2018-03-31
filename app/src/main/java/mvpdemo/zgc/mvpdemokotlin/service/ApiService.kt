package mvpdemo.zgc.mvpdemokotlin.service


import io.reactivex.Flowable
import mvpdemo.zgc.mvpdemokotlin.model.GankDataModel
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Nick on 2017/1/6
 */
interface ApiService {
    /**
     * 获取图片数据列表
     * @param pagesize
     * @param page
     * @return
     */
    @GET("data/福利/{pagesize}/{page}")
    fun getPicList(@Path("pagesize") pagesize: Int, @Path("page") page: Int): Flowable<GankDataModel>

    /**
     * 获取休息视频列表
     * @param pagesize
     * @param page
     * @return
     */
    @GET("data/休息视频/{pagesize}/{page}")
    fun getVideoList(@Path("pagesize") pagesize: Int, @Path("page") page: Int): Flowable<GankDataModel>
}
