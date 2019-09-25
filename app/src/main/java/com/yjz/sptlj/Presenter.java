package com.yjz.sptlj;

import android.content.Context;
import android.util.Log;
import com.yjz.sptlj.server.GameCallBack;
import com.yjz.sptlj.server.GameServer;
import com.yjz.sptlj.server.UiBean;
import com.yjz.sptlj.tool.HttpJsonBean;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-25 11:47
 * @CreateUser: yujinzhao
 */
public class Presenter {

    private final GameView view;
    private final Context context;

    public Presenter(GameView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void createServer() {
        GameServer.INSTANCE.createServer(gameCallBack);
    }

    public void huanzuowei(int index){
        GameServer.INSTANCE.getManager().changeSeat("00fa8dfd9f5b2fd1",index);
    }

    private GameCallBack gameCallBack = new GameCallBack() {

        @Override
        public void msg(@NotNull String data) {

            HttpJsonBean<UiBean> jsonBean = new HttpJsonBean<>(data, UiBean.class);
            UiBean uiBean = jsonBean.getBean();
            if (uiBean == null) return;
            switch (uiBean.type){
                case GameManager.SELECT_SEAT:
                    HttpJsonBean<User> httpJsonBean = new HttpJsonBean<>(uiBean.data,User.class);
                    List<User> users = httpJsonBean.getBeanList();
                    view.showSelectSeat(users);
                    break;
            }


//            Log.e("yjz", data);
        }

        @Override
        public void roomError(@NotNull Exception ex) {
            view.gameError(ex);
        }
    };

    public void destory() {

    }
}
