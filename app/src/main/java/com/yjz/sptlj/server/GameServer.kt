package com.yjz.sptlj.server

import android.util.Log
import com.koushikdutta.async.AsyncServer
import com.koushikdutta.async.callback.CompletedCallback
import com.koushikdutta.async.http.WebSocket
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.yjz.sptlj.GameManager
import com.yjz.sptlj.User
import com.yjz.sptlj.tool.ActionTool
import com.yjz.sptlj.tool.HttpJsonBean

/**
 *
 * @ProjectName:    sptlj
 * @CreateDate:     2019-09-24 22:01
 * @CreateUser:     yujinzhao
 */
object GameServer {

    var httpServer: AsyncHttpServer = AsyncHttpServer()
    val TAG = "GAME_SERVER"
    private lateinit var gameCallBack: GameCallBack


    private val errorCallback = CompletedCallback { ex ->
        Log.e(TAG, ex.toString())
        est()
        gameCallBack.roomError(ex)
    }


    lateinit var manager: GameManager


//    fun changeSeat(user: User,nb:Int){
//
//        if (nb == 0){
//            user.seat = 0
//            manager.
//        }
//
//        for(i in 0..users.size){
//            if (users[i].seat != 0 && )
//        }
//        if ()
//    }

    /**
     *
     */
    fun createServer(gameCallBack: GameCallBack) {

        httpServer.stop()
        this.gameCallBack = gameCallBack;
        manager = GameManager(gameCallBack);
        httpServer.errorCallback = errorCallback
        httpServer.websocket("/live") { webSocket, request ->

            /**
             * 1.是不是房间内用户重连
             *
             * 2.是不是满人了 满人了拒绝 发送房间满员
             *
             * 3.未满人 创建游戏玩家，绑定websocket
             */


//            var user = User(,)

//            _sockets.add(webSocket)
//
//
//            //Use this to clean up any references to your websocket
//            webSocket.closedCallback = CompletedCallback { ex ->
//                try {
//                    if (ex != null)
//                        Log.e("yyyy", "An error occurred", ex)
//                } finally {
//                    Log.e("yyyy", "An error _sockets.remove(webSocket);")
//                    _sockets.remove(webSocket)
//                }
//            }

            webSocket.stringCallback = WebSocket.StringCallback { s ->
                Log.e("yyyy", "收到客户端发送的消息$s")


                var httpJsonBean = HttpJsonBean<ActionBean>(s, ActionBean::class.java)
                var actionBean = httpJsonBean.bean
                if (httpJsonBean.bean == null) return@StringCallback

                when (actionBean.action) {
                    ActionTool.ACTION_JOIN -> {
                        var user = HttpJsonBean<User>(actionBean.data, User::class.java).bean
                        if (user == null) return@StringCallback
                        manager.joinTheRoom(user,webSocket)
                    }
                    ActionTool.ACTION_CHANGE_SEAT ->{
                        manager.changeSeat(actionBean.uid,actionBean.data.toInt())
                    }
                }

            }
        }
        httpServer.listen(AsyncServer.getDefault(), 1234)
    }


    fun est() {

    }
}

