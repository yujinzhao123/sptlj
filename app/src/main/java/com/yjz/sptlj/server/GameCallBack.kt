package com.yjz.sptlj.server

import java.lang.Exception

/**
 *
 * @ProjectName:    sptlj
 * @CreateDate:     2019-09-25 10:38
 * @CreateUser:     yujinzhao
 */
interface GameCallBack {
    fun roomError(ex: Exception)
    fun msg(data: String)

}