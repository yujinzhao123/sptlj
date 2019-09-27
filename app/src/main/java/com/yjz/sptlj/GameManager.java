package com.yjz.sptlj;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.koushikdutta.async.http.WebSocket;
import com.yjz.sptlj.server.Const;
import com.yjz.sptlj.server.GameCallBack;
import com.yjz.sptlj.server.UiBean;
import com.yjz.sptlj.tool.ActionTool;
import com.yjz.sptlj.tool.HttpJsonBean;
import com.yjz.sptlj.tool.UiBeanTool;

import java.util.ArrayList;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-25 12:40
 * @CreateUser: yujinzhao
 */
public class GameManager {


    private final GameCallBack callBack;
    ArrayList<User> users;

    /*** 选座位**/
    public static final int SELECT_SEAT = 1;


    /**
     * 0->选座
     */
    private int gameStatus = 0;

    /*** 创建游戏管理，同时创建主机User
     * @param callBack
     */
    public GameManager(GameCallBack callBack) {
        this.callBack = callBack;
        users = new ArrayList<>();

        User user = new User("主机", Const.Companion.getUid());
        user.setHost(true);
        users.add(user);

        gameStatus = SELECT_SEAT;
        sendSeatUiData();
    }


    public void joinTheRoom(User user, WebSocket webSocket) {

        User u = findUser(user.getId());
        if (u != null) {
            //说明是重连用户，替换最新的socket
            webSocket.send(UiBeanTool.sendToast("又 回来了 老弟？"));
            u.setWebSocket(webSocket);
            return;
        }

        if (gameStatus != SELECT_SEAT || users.size() >= 4) {
            //游戏已经开始了，或者房间满员了，不允许加入
            webSocket.send(UiBeanTool.sendToast("房间已满，GUN~"));
            webSocket.end();
            return;
        }

        //新用户进入
        user.setWebSocket(webSocket);
        users.add(user);
        //
        sendSeatUiData();


    }

    /**
     * 给用户发消息
     */
    public void sendMsg(User user,String data){
        if (user.isHost()){
            callBack.msg(data);
        }else {
            user.getWebSocket().send(data);
        }
    }

    public void changeSeat(String address, int nb) {

        User user = findUser(address);
        if (gameStatus != SELECT_SEAT || user == null) return;

        for (User u : users) {
            if (u.getSeat() == nb) {
                //座位上有人 不往后执行
                sendMsg(user,UiBeanTool.sendToast("座位上有人 心里没点逼数？"));
                return;
            }
        }

        user.setSeat(nb);

        sendSeatUiData();


    }

    /**
     * 给所有用户发送 选座数据
     */
    public void sendSeatUiData() {
        String data = sendZuoWeiMsg();
        for (User u : users) {
            if (u.isHost()) {

                callBack.msg(data);
            } else {
                u.getWebSocket().send(data);
            }
        }
    }

    /**
     * 选座位环节中，需要显示界面的数据对象
     *
     * @return
     */
    public String sendZuoWeiMsg() {

        UiBean uiBean = new UiBean();
        uiBean.type = SELECT_SEAT;
        uiBean.data = JSON.toJSONString(users);
        String test = JSON.toJSONString(users);
        Log.e("hhh", test);
        Log.e("hhh", "Size:" + new HttpJsonBean<User>(test, User.class).getBeanList().size());


        return JSON.toJSONString(uiBean);
    }


    /**
     * 根据address 寻找User
     *
     * @param uid
     * @return
     */
    public User findUser(String uid) {
        for (User u : users) {
            if (u.getId().equals(uid)) {
                return u;
            }
        }
        return null;
    }

    /**
     * 检测是不是房间内用户
     *
     * @param user
     * @return
     */
    public boolean testUser(User user) {
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 开始游戏
     */
    public void startGame() {


    }
}
