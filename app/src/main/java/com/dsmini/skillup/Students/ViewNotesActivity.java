package com.dsmini.skillup.Students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dsmini.skillup.R;

import java.util.Locale;

import es.claucookie.miniequalizerlibrary.EqualizerView;

public class ViewNotesActivity extends AppCompatActivity {

    private TextView txt1,txt2,txt3;
    private TextToSpeech textToSpeech;
    private Button btn;
    private EqualizerView equalizerView;
    private ImageButton imgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        txt1 = findViewById(R.id.txt_title);
        txt2 = findViewById(R.id.txt_module);
        txt3 = findViewById(R.id.txt_note);
        btn = findViewById(R.id.speech);
        equalizerView = findViewById(R.id.imgr);
        imgbtn = findViewById(R.id.puse);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String module = intent.getStringExtra("notemodule");
        String note = intent.getStringExtra("fullnote");

        txt1.setText(name);
        txt2.setText(module);
        txt3.setText(note);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR)
                    textToSpeech.setLanguage(Locale.ENGLISH);

            }

        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String read = txt3.getText().toString();
                textToSpeech.setSpeechRate(0.8f);
                Voice maleVoice = new Voice("en-us-x-sfg#male_1-local", Locale.US, 1, 1, true, null);
                textToSpeech.setVoice(maleVoice);
                equalizerView.setVisibility(View.VISIBLE);

                textToSpeech.speak(read, TextToSpeech.QUEUE_FLUSH, null,"myUtteranceId");
                imgbtn.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
                equalizerView.animateBars();
            }
        });

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.stop();
                btn.setVisibility(View.VISIBLE);
                imgbtn.setVisibility(View.INVISIBLE);
                equalizerView.stopBars();
            }
        });

        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                if (utteranceId.equals("myUtteranceId")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn.setVisibility(View.VISIBLE);
                            btn.setText("Speak again");
                            equalizerView.stopBars();
                            imgbtn.setVisibility(View.INVISIBLE);
                        }
                    });
                }

            }

            @Override
            public void onError(String utteranceId) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        textToSpeech.stop();
        Intent intent = new Intent(ViewNotesActivity.this,NotesActivity.class);
        startActivity(intent);
    }

}