package com.yjz.sptlj.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-29 21:18
 * @CreateUser: yujinzhao
 */
public class CardManager {

    // 2,3,4,5,6,7,8,9,10,J,Q,K,A,JOKER,JOKER

    String[] cardNames = {"2","3","4","5","6","7","8","9","10","J","Q","K","A","JOKER","JOKER"};


    List<Card> cards = new ArrayList<>();

    public CardManager(){
        createCards();
        shuffle();
    }



    /**
     * 创建一副牌 并且洗牌
     */
    public void createCards(){
        cards.clear();
        cards.add(new Card(cardNames[13],13,0));
        cards.add(new Card(cardNames[14],14,0));
        for (int i = 0; i < cardNames.length - 2; i++) {
            cards.add(new Card(cardNames[i],i,1));
            cards.add(new Card(cardNames[i],i,2));
            cards.add(new Card(cardNames[i],i,3));
            cards.add(new Card(cardNames[i],i,4));
        }

    }

    public boolean isNext(){
        if (cards.size()== 3){
            return false;
        }else {
            return true;
        }
    }

    public Card next(){
        Card card = cards.remove(0);
        return card;
    }

    /**
     * 洗牌
     */
    public void shuffle(){
        Collections.shuffle(cards);
    };
}
