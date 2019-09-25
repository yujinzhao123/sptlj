package com.yjz.sptlj.server

import android.util.Log
import com.koushikdutta.async.AsyncServer
import com.koushikdutta.async.callback.CompletedCallback
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.yjz.sptlj.GameManager
import com.yjz.sptlj.User
import java.util.AbstractList
import java.util.ArrayList

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



    lateinit var manager :GameManager





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
        httpServer.websocket(Const.REGEX) { webSocket, request ->

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

//            webSocket.stringCallback = WebSocket.StringCallback { s ->
//                Log.e("yyyy", "oooL$s")
//                //                        if ("Hello Server".equals(s))
//                //                        webSocket.send("Welcome Client!");
//
////                val httpJsonBean = HttpJsonBean(s, MouseBean::class.java)
////                val mouse = httpJsonBean.getBean()
////                if (mouse != null) {
////                    val msg = Message()
////                    val data = Bundle()
////                    data.putString("bean", s)
////                    msg.data = data
////                    handler.sendMessage(msg)
//
//            }
        }
        httpServer.listen(AsyncServer.getDefault(), Const.port)
    }


    fun est() {

    }
}

