package com.yjz.sptlj;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.UiThread;
import android.util.Log;
import android.widget.Toast;
import com.yjz.sptlj.server.Const;
import com.yjz.sptlj.server.GameCallBack;
import com.yjz.sptlj.server.GameServer;
import com.yjz.sptlj.server.UiBean;
import com.yjz.sptlj.tool.HttpJsonBean;
import com.yjz.sptlj.tool.UiBeanTool;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-25 11:47
 * @CreateUser: yujinzhao
 */
public class Presenter {

    public final GameView view;
    public final Context context;
    public final String name;

    public Presenter(GameView view, Context context,String name) {
        this.view = view;
        this.context = context;
        this.name = name;
    }


    public void connectServer() {
        GameServer.INSTANCE.createServer(gameCallBack);
    }

    public void huanzuowei(int index){
        GameServer.INSTANCE.getManager().changeSeat(Const.Companion.getUid(),index);
    }

    public void startGame(){
        GameServer.INSTANCE.getManager().startGame();
    }

    GameCallBack gameCallBack = new GameCallBack() {

        @Override
        public void msg(@NotNull final String data) {

            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    HttpJsonBean<UiBean> jsonBean = new HttpJsonBean<>(data, UiBean.class);
                    UiBean uiBean = jsonBean.getBean();
                    if (uiBean == null) return;
                    switch (uiBean.type){
                        case GameManager.SELECT_SEAT:
                            HttpJsonBean<User> httpJsonBean = new HttpJsonBean<>(uiBean.data,User.class);
                            List<User> users = httpJsonBean.getBeanList();
                            view.showSelectSeat(users);
                            break;
                        case UiBeanTool.ACTION_TOAST:

                            Toast.makeText(context, uiBean.data, Toast.LENGTH_SHORT).show();

                            break;
                    }


                }
            });

        }

        @Override
        public void roomError(@NotNull Exception ex) {
            view.gameError(ex);
        }
    };

    public void destory() {

    }
}
