package com.yjz.sptlj;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yjz.sptlj.tool.Card;

import java.util.List;

/**
 * @ProjectName: sptlj
 * @CreateDate: 2019-09-29 22:31
 * @CreateUser: yujinzhao
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    private final List<Card> cards;
    private final int with;

    public CardAdapter(int with, List<Card> cards){

        this.cards = cards;
        this.with = with;
    }



    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {

    }

    class CardHolder extends RecyclerView.ViewHolder{

        public CardHolder(View itemView) {
            super(itemView);
        }
    }
}
