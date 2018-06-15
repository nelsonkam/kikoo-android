package com.kikoo.kikoo

import android.app.Application
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump



/**
 * Created by nelson on 15/6/18.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ViewPump.init(ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                                .setDefaultFontPath("rubik/Rubik-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build())
    }
}