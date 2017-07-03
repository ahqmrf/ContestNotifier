package apps.ahqmrf.contestnotifier;

import android.app.Application;

import com.binjar.prefsdroid.Preference;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import apps.ahqmrf.contestnotifier.utils.PrefKeys;

/**
 * Created by bsse0 on 6/29/2017.
 */

public class App extends Application {

    public static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);

        Preference.load().using(this).with(PrefKeys.NAME).prepare();
    }

    public static App getContext() {
        return context;
    }
}
