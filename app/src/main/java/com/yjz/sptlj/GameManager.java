package com.yjz.sptlj;

import com.alibaba.fastjson.JSON;
import com.koushikdutta.async.http.WebSocket;
import com.yjz.sptlj.server.Const;
import com.yjz.sptlj.server.GameCallBack;
import com.yjz.sptlj.server.UiBean;
import com.yjz.sptlj.tool.Card;
import com.yjz.sptlj.tool.CardManager;
import com.yjz.sptlj.tool.UiBeanTool;

import java.util.*;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-25 12:40
 * @CreateUser: yujinzhao
 */
public class GameManager {


    private final GameCallBack callBack;
    ArrayList<User> users;
    private boolean isGameStart = false;//游戏是不是已经开始了

    /*** 选座位**/
    public static final int SELECT_SEAT = 1;
    /**
     * 发牌
     **/
    public static final int DEAL_CARDS = 2;
    /*** 0->选座*/
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


    /**
     * 用户加入房间
     *
     * @param user
     * @param webSocket
     */
    public synchronized void joinTheRoom(User user, WebSocket webSocket) {

        User u = findUser(user.getId());
        if (u != null) {
            //说明是重连用户，替换最新的socket
            webSocket.send(UiBeanTool.sendToast("又 回来了 老弟？"));
            u.setWebSocket(webSocket);
            return;
        }

        if (gameStatus != SELECT_SEAT || users.size() >= 3) {
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
    public void sendMsg(User user, String data) {
        if (user.isHost()) {
            callBack.msg(data);
        } else {
            if (user.getWebSocket() != null) {
                user.getWebSocket().send(data);
            }

        }
    }

    /**
     * 修改座位
     *
     * @param address
     * @param nb
     */
    public void changeSeat(String address, int nb) {

        User user = findUser(address);
        if (gameStatus != SELECT_SEAT || user == null) return;

        for (User u : users) {
            if (u.getSeat() == nb) {
                //座位上有人 不往后执行
                sendMsg(user, UiBeanTool.sendToast("座位上有人 心里没点逼数？"));
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
            sendMsg(u, data);

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


        if (isGameStart)
            return;


//        int count = 0;//正式作为上的人数
//        for (User u : users) {
//            if (u.getSeat() != 0)
//                count++;
//        }

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "");
        map.put(2, "");
        map.put(3, "");


        //测试用的，没到4个人 创建假玩家
        for (int i = 0; i < users.size(); i++) {
            map.remove(users.get(i).getSeat());
        }

        for (Integer key : map.keySet()) {
            User user = new User("电脑" + key, key + "sp");
            user.setSeat(key);
            users.add(user);
        }

        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            User u = it.next();
            if (u.getSeat() == 0) {
                try {
                    u.getWebSocket().end();
                } catch (Exception e) {

                }
                it.remove();
            }
        }

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getSeat() - o1.getSeat();
            }
        });


        gameStatus = DEAL_CARDS;
        isGameStart = true;
        startGameThread();

    }

    private void startGameThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (isGameStart) {

                    switch (gameStatus) {
                        case DEAL_CARDS:
                            dealCards();
                            break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    CardManager cardManager = new CardManager();

    /**
     * 发牌
     */
    private void dealCards() {

        if (cardManager.isNext()) {
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                u.getCards().add(cardManager.next());
            }
            senMsgAll(UiBeanTool.dealCardUi(users));
        }

    }

    public void senMsgAll(String data) {
        for (User u : users) {
            sendMsg(u, data);
        }
    }
}
