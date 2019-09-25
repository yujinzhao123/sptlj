package com.yjz.sptlj

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//这句代码必须写在setContentView()前面
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)



        btn.setOnClickListener {
            this@MainActivity.startActivity(Intent(this@MainActivity,GameActivity::class.java))

        }

    }
}
