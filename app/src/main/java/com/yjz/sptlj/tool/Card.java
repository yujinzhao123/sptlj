package com.yjz.sptlj.tool;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-29 21:00
 * @CreateUser: yujinzhao
 */
public class Card {

    public Card(){

    }

    public Card(String name,int value,int color){
        this.name = name;
        this.value = value;
        this.color = color;
    }

    //♠ ♥ ♣ ♦ 1，2，3，4
    public int color;
    public String name;
    public int value;
}
