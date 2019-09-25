package com.yjz.sptlj

import com.alibaba.fastjson.annotation.JSONField
import com.koushikdutta.async.http.WebSocket

/**
 *
 * @ProjectName:    sptlj
 * @CreateDate:     2019-09-25 09:42
 * @CreateUser:     yujinzhao
 */
class User {


    /**
     * 是不是主机
     */
    var isHost = false

    @JSONField(serialize=false)
    var webSocket: WebSocket? = null

    var name: String

    var id: String

    /**
     * 0未上座，总共1234座位
     */
    var seat:Int = 0

    constructor(){
        this.name = ""
        this.id = ""
    }

    constructor(name: String, id: String) {
        this.name = name
        this.id = id
    }

    /**
     * 根据id，判断是不是自己
     */
    fun isMy(): Boolean {
        return (android.os.Build.SERIAL).equals(id)
    }

}