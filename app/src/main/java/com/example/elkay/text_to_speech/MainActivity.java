package com.example.elkay.text_to_speech;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech mttS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch, mSeekBarSpeed;
    private Button mButtonSpeak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSpeak = findViewById(R.id.button_speak);

        mttS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = mttS.setLanguage(Locale.ENGLISH);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","Language Not Supported");
                    }else{
                        mButtonSpeak.setEnabled(true);
                    }
                }else{
                    Log.e("TTS","Initialization failed");
                }
            }
        });

        mEditText = findViewById(R.id.edit_text);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }
    private void speak(){
        String text = mEditText.getText().toString();
        float pitch = (float)mSeekBarPitch.getProgress()/50;
        if(pitch < 0.1) pitch = 0.1f;

        float speed = (float)mSeekBarSpeed.getProgress()/50;
        if(speed < 0.1) speed = 0.1f;

        mttS.setPitch(pitch);
        mttS.setSpeechRate(speed);

        mttS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {

        if(mttS!=null){
            mttS.stop();
            mttS.shutdown();
        }
        super.onDestroy();
    }
}
