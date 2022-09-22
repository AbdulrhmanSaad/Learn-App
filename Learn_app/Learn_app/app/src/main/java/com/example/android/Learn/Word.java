package com.example.android.Learn;

import android.media.MediaPlayer;

public class Word {
    private String mArabicTranslation;
    private String mEEnglishTranslation;
    private int imageSource;
    private int audioId;
    private MediaPlayer mediaPlayer;

    public Word(String mEEnglishTranslation,String mArabicTranslation, int imageSource,int audioId) {
        this.mArabicTranslation = mArabicTranslation;
        this.mEEnglishTranslation= mEEnglishTranslation;
        this.imageSource=imageSource;
        this.audioId=audioId;
    }
    public Word( String mEEnglishTranslation,String mArabicTranslation,int audioId) {
        this.mArabicTranslation = mArabicTranslation;
        this.mEEnglishTranslation = mEEnglishTranslation;
        this.imageSource=0;
        this.audioId=audioId;
    }
    public int getImageSource() {
        return imageSource;
    }

    public int getAudioId() {
        return audioId;
    }

    public String getmArabicTranslation() { return mArabicTranslation; }

    public String getmEEnglishTranslation() {
        return mEEnglishTranslation;
    }
}
