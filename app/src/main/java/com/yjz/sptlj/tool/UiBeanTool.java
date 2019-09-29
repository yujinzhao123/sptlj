package com.yjz.sptlj.tool;

import com.alibaba.fastjson.JSON;
import com.yjz.sptlj.User;
import com.yjz.sptlj.server.UiBean;

import java.util.List;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-26 22:50
 * @CreateUser: yujinzhao
 */
public class UiBeanTool {

    public static final int ACTION_TOAST = 111;
    public static final int DEAL_CARD = ACTION_TOAST + 1;

    /**
     * 发送toast
     *
     * @param msg
     * @return
     */
    public static String sendToast(String msg) {
        UiBean bean = new UiBean();
        bean.type = ACTION_TOAST;
        bean.data = msg;
        return JSON.toJSONString(bean);
    }

    /**
     *  发牌阶段显示UI
     * @param user
     * @return
     */
    public static String dealCardUi(List<User> user) {
        UiBean bean = new UiBean();
        bean.type = DEAL_CARD;
        bean.data = JSON.toJSONString(user);
        return JSON.toJSONString(bean);
    }
}
