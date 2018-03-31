package mvpdemo.zgc.mvpdemokotlin.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import mvpdemo.zgc.mvpdemokotlin.di.scope.ActivityScoped
import mvpdemo.zgc.mvpdemokotlin.ui.activity.HomeActivity
import mvpdemo.zgc.mvpdemokotlin.ui.activity.PhotoViewActivity

/**
 * Created by Nick on 2017/12/2
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(DefaultActivityModule::class))
    abstract fun homeActivity(): HomeActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = arrayOf(DefaultActivityModule::class))
    abstract fun photoViewActivity(): PhotoViewActivity

}
