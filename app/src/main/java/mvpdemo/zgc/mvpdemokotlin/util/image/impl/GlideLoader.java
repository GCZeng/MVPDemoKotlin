package mvpdemo.zgc.mvpdemokotlin.util.image.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestListener;

import javax.inject.Inject;

import mvpdemo.zgc.mvpdemokotlin.util.image.ILoader;
import mvpdemo.zgc.mvpdemokotlin.util.image.ImageLoader;
import mvpdemo.zgc.mvpdemokotlin.util.image.ImageManager;


/**
 * Created by Nick on 2017/12/2
 */
@GlideModule
public class GlideLoader extends AppGlideModule implements ILoader {
    @Inject
    public GlideLoader() {
    }

    @Override
    public void load(Context context, String url, ImageView imageView) {
        load(context, url, imageView, -1, null);
    }

    @Override
    public void load(Context context, String url, ImageView imageView, int placeHolder) {
        load(context, url, imageView, placeHolder, null);
    }

    @Override
    public void load(Context context, String url, ImageView imageView, ImageManager.ScaleType scaleType) {
        load(context, url, imageView, -1, scaleType);
    }

    @Override
    public void load(Context context, String url, ImageView imageView, int placeHolder, ImageManager.ScaleType scaleType) {
        load(context, url, imageView, placeHolder, scaleType, null);

    }

    @Override
    public void load(Context context, String url, ImageView imageView, int placeHolder, ImageManager.ScaleType scaleType, RequestListener listener) {
        GlideRequest glideRequest = getGlideRequest(context, url, placeHolder, scaleType, listener);

        glideRequest.into(imageView);
    }

    @Override
    public void preload(Context context, String url, int placeHolder, ImageManager.ScaleType scaleType, RequestListener listener) {
        GlideRequest glideRequest = getGlideRequest(context, url, placeHolder, scaleType, listener);

        glideRequest.preload();
    }

    @NonNull
    private GlideRequest getGlideRequest(Context context, String url, int placeHolder, ImageManager.ScaleType scaleType, RequestListener listener) {
        GlideRequest glideRequest = GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        glideRequest.placeholder(placeHolder > 0 ? placeHolder : ImageLoader.mDefaultPlaceHolder);

        if (listener != null) {
            glideRequest.listener(listener);
        }

        if (scaleType != null) {
            switch (scaleType) {
                case CENTER_CROP:
                    glideRequest.centerCrop();
                    break;
                case CENTER_INSIDE:
                    glideRequest.centerInside();
                    break;
                case FIT_CENTER:
                    glideRequest.fitCenter();
                    break;
                case CIRCLY_CROP:
                    glideRequest.circleCrop();
                    break;
            }
        }
        return glideRequest;
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int diskCacheSizeBytes = 1024 * 1024 * 100;
        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context, "MVPDemo", diskCacheSizeBytes));
    }

}
