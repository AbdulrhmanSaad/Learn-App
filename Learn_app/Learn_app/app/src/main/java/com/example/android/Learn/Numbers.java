package com.example.android.Learn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Numbers extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }else if(i==AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }else if(i==AudioManager.AUDIOFOCUS_LOSS)
                releaseMediaPlayer();
        }
        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ArrayList<Word> words=new ArrayList<>();
        //String [] words=new String[10];
        words.add(new Word("One","واحد",R.drawable.number_one,R.raw.one));
        words.add(new Word("Two","اتنين",R.drawable.number_two,R.raw.two));
        words.add(new Word("Three","ثلاثه",R.drawable.number_three,R.raw.three));
        words.add(new Word("Four","اربعه",R.drawable.number_four,R.raw.four));
        words.add(new Word("Five","خمسه",R.drawable.number_five,R.raw.five));
        words.add(new Word("Six","سته",R.drawable.number_six,R.raw.six));
        words.add(new Word("Seven","سبعه",R.drawable.number_seven,R.raw.seven));
        words.add(new Word("Eight","ثمانيه",R.drawable.number_eight,R.raw.eight));
        words.add(new Word("Nine","تسعه",R.drawable.number_nine,R.raw.nine));
        words.add(new Word("Ten","عشره",R.drawable.number_ten,R.raw.ten));


        WordAdapter itemsAdapter = new WordAdapter(this, words,R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                Word w = words.get(i);
                mediaPlayer = MediaPlayer.create(Numbers.this, w.getAudioId());
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(Numbers.this, w.getAudioId());
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
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}