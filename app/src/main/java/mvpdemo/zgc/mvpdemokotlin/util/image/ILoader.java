package mvpdemo.zgc.mvpdemokotlin.util.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;

/**
 * Created by Nick on 2017/12/2
 */
public interface ILoader {
    void load(Context context, String url, ImageView imageView);

    void load(Context context, String url, ImageView imageView, int placeHolder);

    void load(Context context, String url, ImageView imageView, ImageManager.ScaleType scaleType);

    void load(Context context, String url, ImageView imageView, int placeHolder, ImageManager.ScaleType scaleType);

    void load(Context context, String url, ImageView imageView, int placeHolder, ImageManager.ScaleType scaleType, RequestListener listener);

    void preload(Context context, String url, int placeHolder, ImageManager.ScaleType scaleType, RequestListener listener);
}
