package com.yjz.sptlj

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*

/**
 *
 * @ProjectName:    sptlj
 * @CreateDate:     2019-09-25 10:28
 * @CreateUser:     yujinzhao
 *
 * 用mvp模式
 * 1.根据是不是房主创建不同的p，
 * V：更加游戏进行步骤显示对应的画面
 *      1.选座
 *      2.发牌。。。。
 *      3.网络断开，退出界面
 *
 *
 *
 *
 */
class GameActivity : AppCompatActivity(), GameView {


    override fun showSelectSeat(users: MutableList<User>) {

        runOnUiThread {
            for (zw in zws) {
                zw.text = ""
            }

            var isMyZw0 = false
            var my: User? = null
            for (u in users) {

                if (u.isMy() && u.seat == 0) {
                    isMyZw0 = true
                    my = u;
                }

                zws[u.seat].text = u.name
            }
            if (isMyZw0 && my != null) {
                zws[0].text = my.name
            }
        }


    }

    override fun gameError(e: Exception?) {
        runOnUiThread {
            Toast.makeText(this, "哦豁~房间炸了Boom！", Toast.LENGTH_LONG).show()
        }

        finish()
    }

    private lateinit var presenter: Presenter
    var isHost = false
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        name = intent.getStringExtra("name")

        initView()

        isHost = intent.getBooleanExtra("isHost", false)
        if (isHost) {
            presenter = Presenter(this, this,"")
            startBtn.visibility = View.VISIBLE
            startBtn.setOnClickListener {
                presenter.startGame()
            }
        }else{
            presenter =ClientPresenter(this,this,name)
        }
        presenter.connectServer()


    }

    lateinit var zws: Array<TextView>
    private fun initView() {

        zws = arrayOf(weizhi0, weizhi1, weizhi2, weizhi3, weizhi4)
        for (i in zws.indices) {
            zws[i].setOnClickListener { presenter.huanzuowei(i) }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.destory()
    }


}