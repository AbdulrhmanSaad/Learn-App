package com.example.android.Learn;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;
    //private static final String LOG_TAG = WordAdapter.class.getSimpleName();

    public WordAdapter(Activity context,ArrayList<Word>word,int mColorResourceId) {
        super(context,0 ,word);
        this.mColorResourceId=mColorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item, parent, false);
        }

        Word currentWordAdapter =getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.english);
        nameTextView.setText(currentWordAdapter.getmEEnglishTranslation());

        TextView nameTextView2 = (TextView) listItemView.findViewById(R.id.arabic);
        nameTextView2.setText(currentWordAdapter.getmArabicTranslation());
        ImageView imageView=(ImageView) listItemView.findViewById(R.id.image);
        //ImageView arrow=listItemView.findViewById(R.id.arrow);
         if(currentWordAdapter.getImageSource()==0){
            imageView.setVisibility(View.GONE); }
         else {
               imageView.setImageResource(currentWordAdapter.getImageSource());
         }
        View text_container=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mColorResourceId);
        text_container.setBackgroundColor(color);
        return listItemView;
    }
}
