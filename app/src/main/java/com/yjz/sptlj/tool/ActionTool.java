package com.yjz.sptlj.tool;

import com.alibaba.fastjson.JSON;
import com.yjz.sptlj.User;
import com.yjz.sptlj.server.ActionBean;
import com.yjz.sptlj.server.Const;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-26 21:40
 * @CreateUser: yujinzhao
 */
public class ActionTool {

    /**
     * 加入房间
     */
    public static final int ACTION_JOIN = 0;
    /**
     * 换座位
     **/
    public static final int ACTION_CHANGE_SEAT = 1;

    public static String changeSeat(String id, int nb) {
        ActionBean bean = new ActionBean();
        bean.action = ACTION_CHANGE_SEAT;
        bean.uid = id;
        bean.data = nb + "";

        return JSON.toJSONString(bean);
    }


    public static String joinRoom(String name) {
        ActionBean bean = new ActionBean();
        bean.action = ACTION_JOIN;

        User user = new User(name, Const.Companion.getUid());
        bean.data = JSON.toJSONString(user);
        return JSON.toJSONString(bean);

    }
}
