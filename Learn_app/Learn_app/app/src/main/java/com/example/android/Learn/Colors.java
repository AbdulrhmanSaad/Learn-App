package com.example.android.Learn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Colors extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    // to release media player after the voice ended
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
       final ArrayList<Word> words=new ArrayList<>();
        words.add(new Word("Black","اسود",R.drawable.color_black,R.raw.black));
        words.add(new Word("Brown","بني",R.drawable.color_brown,R.raw.brown));
        words.add(new Word("Gray","رمادي", R.drawable.color_gray,R.raw.gray));
        words.add(new Word("Green","اخضر",R.drawable.color_green,R.raw.green));
        words.add(new Word("Yellow","اصفر",R.drawable.color_mustard_yellow,R.raw.yellow));
        words.add(new Word("Red","احمر",R.drawable.color_red,R.raw.red));
        words.add(new Word("White","ابيض",R.drawable.color_white,R.raw.white));
        words.add(new Word("Dusty yellow","اصفر مغبر", R.drawable.color_dusty_yellow,R.raw.dusty));

        WordAdapter itemsAdapter = new WordAdapter(this, words,R.color.category_colors);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                Word w = words.get(i);
                mediaPlayer = MediaPlayer.create(Colors.this, w.getAudioId());
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                    mediaPlayer = MediaPlayer.create(Colors.this, w.getAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }
    // to release media player if the user left the app
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
// to release media player
    private void releaseMediaPlayer() {

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}