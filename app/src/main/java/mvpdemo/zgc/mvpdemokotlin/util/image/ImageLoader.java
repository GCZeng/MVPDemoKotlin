package mvpdemo.zgc.mvpdemokotlin.util.image;

import android.content.Context;

/**
 * Created by Nick on 2017/12/2
 */
public class ImageLoader {
    public static int mDefaultPlaceHolder = -1;

    public static void init(int placeHolder) {
        mDefaultPlaceHolder = placeHolder;
    }

    public static ImageManager.Builder with(Context context) {
        return new ImageManager.Builder(context);
    }


}
