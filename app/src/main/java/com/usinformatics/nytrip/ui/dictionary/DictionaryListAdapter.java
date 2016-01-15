package com.usinformatics.nytrip.ui.dictionary;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.usinformatics.nytrip.R;

import java.util.ArrayList;

import common.views.FontTextView;

/**
 * Created by admin on 7/16/15.
 */
public class DictionaryListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<WordModel> wordsList;
    private Holder holder;

    DictionaryListAdapter(Context context, ArrayList<WordModel> wordsList) {
        mContext = context;
        this.wordsList = wordsList;
    }

    @Override
    public int getCount() {
        return wordsList.size();
    }

    @Override
    public Object getItem(int position) {
        return wordsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layout = ((Activity) mContext).getLayoutInflater();
            convertView = layout.inflate(R.layout.item_dictionary_expandable_list_item, parent, false);
            holder = new Holder();
            holder.word = ((FontTextView) convertView.findViewById(R.id.dictionary_word));
            holder.transcription = ((FontTextView) convertView.findViewById(R.id.dictionary_word_transcription));
            holder.translation = ((FontTextView) convertView.findViewById(R.id.dictionary_word_translation));
            holder.description = ((FontTextView) convertView.findViewById(R.id.dictionary_word_description));
            holder.addMoreInfo = ((CardView) convertView.findViewById(R.id.dictionary_add_more_info));
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
// need to uncomment when you get real data
//        holder.word.setText(wordsList.get(position).getWord());
//        holder.transcription.setText(wordsList.get(position).getTranscription());
//        holder.translation.setText(wordsList.get(position).getTranslation());
//        holder.description.setText(wordsList.get(position).getDescription());
        holder.addMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    private class Holder {
        FontTextView word;
        FontTextView transcription;
        FontTextView translation;
        FontTextView description;
        CardView addMoreInfo;
    }
}
