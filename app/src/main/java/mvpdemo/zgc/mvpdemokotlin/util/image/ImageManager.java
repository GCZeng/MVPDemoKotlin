package mvpdemo.zgc.mvpdemokotlin.util.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;

import mvpdemo.zgc.mvpdemokotlin.util.image.impl.GlideLoader;


/**
 * Created by Nick on 2017/12/3
 */
public class ImageManager {
    private static ILoader mILoader;

    static {
        mILoader = new GlideLoader();
    }

    public static class Builder {
        private Context mContext;
        private String mUrl;
        private int mPlaceHolder;
        private ScaleType scaleType;
        private RequestListener listener;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder url(String url) {
            this.mUrl = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.mPlaceHolder = placeHolder;
            return this;
        }

        /**
         * @param scaleType
         * @return
         */
        public Builder scaleType(ScaleType scaleType) {
            this.scaleType = scaleType;
            return this;
        }

        public Builder listener(RequestListener listener) {
            this.listener = listener;
            return this;
        }

        public void into(ImageView imageView) {

            mILoader.load(mContext, mUrl, imageView, mPlaceHolder, scaleType,listener);
        }

        public void preload(){
            mILoader.preload(mContext, mUrl, mPlaceHolder, scaleType,listener);
        }

    }

    public enum ScaleType {
        FIT_CENTER(0),
        CENTER_CROP(1),
        CENTER_INSIDE(2),
        CIRCLY_CROP(3);


        ScaleType(int ni) {
            nativeInt = ni;
        }

        final int nativeInt;
    }

}
