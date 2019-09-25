package com.yjz.sptlj;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-25 11:48
 * @CreateUser: yujinzhao
 */
public interface GameView {

    void gameError(Exception e);

    void showSelectSeat(@NonNull List<User> users);
}
