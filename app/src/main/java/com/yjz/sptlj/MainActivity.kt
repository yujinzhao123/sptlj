package com.yjz.sptlj

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//这句代码必须写在setContentView()前面
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)



        btn.setOnClickListener {
            this@MainActivity.startActivity(Intent(this@MainActivity,GameActivity::class.java)
                .putExtra("isHost",true)
                .putExtra("name","??")
            )


        }
        addRoomBtn.setOnClickListener{

            name = nameEt.text.toString();

            if (name == null || name.equals("")){
                Toast.makeText(this@MainActivity,"输入名字老弟",Toast.LENGTH_LONG).show()

            }else{
                this@MainActivity.startActivity(Intent(this@MainActivity,GameActivity::class.java)
                    .putExtra("name",name))
            }


        }

    }
}
