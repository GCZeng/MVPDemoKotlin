package mvpdemo.zgc.mvpdemokotlin.di.component

import javax.inject.Singleton

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import mvpdemo.zgc.mvpdemokotlin.app.APP
import mvpdemo.zgc.mvpdemokotlin.di.module.ActivityBindingModule
import mvpdemo.zgc.mvpdemokotlin.di.module.AppModule
import mvpdemo.zgc.mvpdemokotlin.di.module.NetworkModule

/**
 * Created by Nick on 2017/12/1
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class,
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class
))
interface AppComponent : AndroidInjector<APP> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<APP>()

}
