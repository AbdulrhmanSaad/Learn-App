package com.example.android.Learn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class phrases extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    ImageView arrow;
    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(i==AudioManager.AUDIOFOCUS_GAIN)
                mediaPlayer.start();
            else if(i==AudioManager.AUDIOFOCUS_LOSS)
                releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        ArrayList<Word> words=new ArrayList<>();
        words.add(new Word("Where are you going?","الي اين انت ذاهب؟",R.raw.p1));
        words.add(new Word("What is your name?","ما اسمك؟",R.raw.p2));
        words.add(new Word("My name is...","اسمي...",R.raw.p3));
        words.add(new Word("How are you?","كيف حالك؟",R.raw.p4));
        words.add(new Word("I’m feeling good.","كويس",R.raw.p5));
        words.add(new Word("Are you coming?","هل ستاتي؟",R.raw.p6));
        words.add(new Word("Yes, I’m coming.","نعم انا قادم",R.raw.p7));
        words.add(new Word("Let’s go.","هيا بنا",R.raw.p8));
        words.add(new Word("Come here.","تعالي هنا",R.raw.p9));


        WordAdapter itemsAdapter = new WordAdapter(this, words,R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                Word w = words.get(i);
                mediaPlayer = MediaPlayer.create(phrases.this, w.getAudioId());
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(phrases.this, w.getAudioId());

                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            //arrow.setImageResource(R.drawable.baseline_arrow_right_white_24);
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}