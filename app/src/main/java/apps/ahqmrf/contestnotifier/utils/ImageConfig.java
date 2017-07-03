package apps.ahqmrf.contestnotifier.utils;

/**
 * Created by bsse0 on 7/3/2017.
 */
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import apps.ahqmrf.contestnotifier.R;

public class ImageConfig {
    private        DisplayImageOptions displayImageOptions;
    private static ImageConfig         mInstance;

    private ImageConfig() {
        displayImageOptions = getImageConfig();
    }

    public static synchronized ImageConfig getInstance() {
        if(mInstance == null) {
            mInstance = new ImageConfig();
        }
        return mInstance;
    }

    public DisplayImageOptions getImageConfig() {
        if(displayImageOptions == null) {
            displayImageOptions = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.placeholder)
                    .showImageOnFail(R.drawable.placeholder)
                    .showImageOnLoading(R.drawable.placeholder)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .resetViewBeforeLoading(true)
                    .build();
        }
        return displayImageOptions;
    }
}
