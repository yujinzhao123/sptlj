package com.yjz.sptlj.tool;

import com.alibaba.fastjson.JSON;
import com.yjz.sptlj.server.UiBean;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-26 22:50
 * @CreateUser: yujinzhao
 */
public class UiBeanTool {

        public static final int ACTION_TOAST = 111;

    /**
     * 发送toast
     * @param msg
     * @return
     *
     * todo 应该放在UIBean里 不属于动作
     */
    public static String sendToast(String msg){
        UiBean bean = new UiBean();
        bean.type = ACTION_TOAST;
        bean.data = msg;
        return JSON.toJSONString(bean);
    }
}
