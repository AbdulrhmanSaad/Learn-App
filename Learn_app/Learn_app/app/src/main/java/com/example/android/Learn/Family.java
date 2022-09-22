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

public class Family extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

         final ArrayList<Word> words=new ArrayList<>();
        words.add(new Word("Father","ابي",R.drawable.family_father,R.raw.father));
        words.add(new Word("Mother","امي",R.drawable.family_mother,R.raw.mother));
        words.add(new Word("Daughter","بنت",R.drawable.family_daughter,R.raw.dauther));
        words.add(new Word("Son","ابن",R.drawable.family_son,R.raw.son));
        words.add(new Word("Young Brother","الاخ الاصغر",R.drawable.family_younger_brother,R.raw.youngbrother));
        words.add(new Word("Order Brother","الاخ الاكبر",R.drawable.family_older_brother,R.raw.orderbrother));
        words.add(new Word("Young Sister","الاخت الاصغر",R.drawable.family_younger_sister,R.raw.youngsister));
        words.add(new Word("Order Sister","الاخت الاكبر",R.drawable.family_older_sister,R.raw.ordersister));
        words.add(new Word("Grand Father","الجد",R.drawable.family_grandfather,R.raw.grandfather));
        words.add(new Word("Grand Mother","الجده",R.drawable.family_grandmother,R.raw.grandmother));


        WordAdapter itemsAdapter = new WordAdapter(this, words,R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                Word w = words.get(i);
                mediaPlayer = MediaPlayer.create(Family.this, w.getAudioId());
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(Family.this, w.getAudioId());
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