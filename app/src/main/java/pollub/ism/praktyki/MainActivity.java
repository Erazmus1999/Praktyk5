package pollub.ism.praktyki;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.IOException;

import static java.security.AccessController.getContext;




public class MainActivity extends AppCompatActivity {

    public static String KLUCZ_WIADOMOSCI = "Przekazywana informacja";

    private Toolbar toolbar;

    private static int MICROPHONE_PERMISSION_CODE = 200;

    private MediaRecorder mediaRecorder;
    private String recordFile;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ConstraintLayout constraintLayout = findViewById(R.id.linearLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(6000);
        animationDrawable.start();

        recordButton();
    }

    public void runMain(View view){
        setContentView(R.layout.activity_main);
        recordButton();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void recordButton()
    {
        ImageButton b1 = findViewById(R.id.RecordButton);
        b1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Chronometer c = findViewById(R.id.counter);
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        // touch down code

                            if (isMicrophonePresent())
                            {
                                getMicrophonePermission();
                                if (checkMicrophonePermission()){
                                    c.setBase(android.os.SystemClock.elapsedRealtime());
                                    c.start();
                                    startRecording();                                             //<----------------recording function HERE
                                    c.setVisibility(View.VISIBLE);
                                    b1.setScaleX(2);
                                    b1.setScaleY(2);
                                }
                                else{
                                    getMicrophonePermission();
                                }
                            }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // touch move code
                        break;

                    case MotionEvent.ACTION_UP:
                        // touch up code
                        b1.setScaleX(1);
                        b1.setScaleY(1);
                        if (checkMicrophonePermission()){
                            stopRecording();                                                      //<----------------recording function HERE
                            c.stop();
                            CharSequence result = c.getText();
                            goToSummary(result);
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private void startRecording() {
        String recordPath = this.getExternalFilesDir("/").getAbsolutePath();
        recordFile = "filename.3gp";

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + "/" + recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    public void goToSummary(CharSequence result){
        Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);
        myIntent.putExtra(KLUCZ_WIADOMOSCI, result);
        MainActivity.this.startActivity(myIntent);
    }

    public void goToSearching(View view) {
        Intent myIntent = new Intent(this, SearchActivity.class);
        startActivity(myIntent);
    }

    private boolean isMicrophonePresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            return true;
        }
        else {
            return false;
        }
    }

    private void getMicrophonePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_CODE);
        }
    }

    private boolean checkMicrophonePermission(){
        boolean a = false;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
            a = false;
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){
            a = true;
        }
        return a;
    }
}