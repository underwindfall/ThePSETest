package com.qifan.thepsetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qifan.thepsetest.ui.main.MainFragment

class BookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}
