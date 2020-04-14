package com.example.minisportapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minisportapp.mvasterisk.mvc.TopicItemListMVCActivity
import com.example.minisportapp.mvasterisk.mvp.TopicItemListMVPActivity
import kotlinx.android.synthetic.main.activity_screen_chooser.*

class ScreenChooserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_chooser)

        buttonMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        buttonMvp.setOnClickListener {
            startActivity(Intent(this, TopicItemListMVPActivity::class.java))
        }
        buttonMvc.setOnClickListener {
            startActivity(Intent(this, TopicItemListMVCActivity::class.java))
        }
    }
}
