package com.yjz.sptlj;

import android.content.Context;
import android.util.Log;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.yjz.sptlj.server.Const;
import com.yjz.sptlj.tool.ActionTool;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-26 20:41
 * @CreateUser: yujinzhao
 */
public class ClientPresenter extends Presenter {

    private boolean isConnect = false;
    private WebSocket socket;
    private long boomTime;

    public ClientPresenter(GameView view, Context context, String name) {
        super(view, context,name);
    }

    @Override
    public void connectServer() {

        connect();


    }

    @Override
    public void huanzuowei(int index) {

    }

    private void connect() {


        AsyncHttpClient.getDefaultInstance().websocket("ws://192.168.0.103:1234/live", (String) null, new AsyncHttpClient.WebSocketConnectCallback() {

            @Override
            public void onCompleted(Exception ex, final WebSocket webSocket) {
                if (ex != null) {
                    Log.e("yyyyy", "onCom:", ex);
                    ex.printStackTrace();

                    gameCallBack.roomError(ex);
                    return;
                }
                isConnect = true;

                socket = webSocket;

                webSocket.send(ActionTool.joinRoom(name));

//

                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    public void onStringAvailable(String s) {
                        gameCallBack.msg(s);
                    }
                });
                webSocket.setDataCallback(new DataCallback() {
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList byteBufferList) {

                        byteBufferList.recycle();
                    }
                });
                webSocket.setClosedCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {
                        isConnect = false;
                        gameCallBack.roomError(ex);
                    }
                });
                webSocket.setEndCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {
                        Log.e("yyyyy", "setEndCallback:ex:" + ex.getLocalizedMessage());
                        isConnect = false;

                    }
                });
                webSocket.setPingCallback(new WebSocket.PingCallback() {
                    @Override
                    public void onPingReceived(String s) {
                        Log.e("yyyyy", "PingCallback::" + s);
                    }
                });
                webSocket.setPongCallback(new WebSocket.PongCallback() {
                    @Override
                    public void onPongReceived(String s) {
                        Log.e("yyyyy", "PongCallback::" + s);
                        boomTime = System.currentTimeMillis();
                    }
                });


            }
        });
    }
}
