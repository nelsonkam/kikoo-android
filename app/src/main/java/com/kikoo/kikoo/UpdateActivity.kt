package com.kikoo.kikoo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_update.*
import android.content.Intent
import android.net.Uri
import io.github.inflationx.viewpump.ViewPumpContextWrapper


class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        button.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://kikoo.surge.sh"))
            startActivity(i)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}
