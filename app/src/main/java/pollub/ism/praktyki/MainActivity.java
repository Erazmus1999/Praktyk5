package pollub.ism.praktyki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.MessageQueue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import pollub.ism.praktyki.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    public static String KLUCZ_WIADOMOSCI = "Przekazywana informacja";
    public static String KLUCZ_WIADOMOSCI2 = "Przekazywana informacja2";
    public static String KLUCZ_WIADOMOSCI3 = "Przekazywana informacja3";

    private Toolbar toolbar;

    private static int MICROPHONE_PERMISSION_CODE = 200;

    private MediaRecorder mediaRecorder;
    private String recordFile;
    private String recordPath;
    private boolean error = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.linearLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(6000);
        animationDrawable.start();

        toolbar=findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);


        recordButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.search:
                Intent myIntent = new Intent(this, SearchActivity.class);
                startActivity(myIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void runMain(View view){
        setContentView(R.layout.activity_main);
        recordButton();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void recordButton()
    {
        ImageButton b1 = findViewById(R.id.RecordButton);
        try {
            b1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Chronometer c = findViewById(R.id.counter);
                    switch(event.getAction()){

                        case MotionEvent.ACTION_DOWN:
                            // touch down code
                            error = false;
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
                                //<----------------recording function HERE
                                c.stop();
                                stopRecording();
                                c.setVisibility(View.INVISIBLE);
                                if (!error)
                                {
                                    CharSequence result = c.getText();
                                    goToSummary(result);
                                }

                            }
                            break;
                    }
                    return false;
                }
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    private void stopRecording() {

        try {
            mediaRecorder.stop();
        } catch(RuntimeException e) {
            Toast.makeText(this, "Zbyt krotkie nagranie!", Toast.LENGTH_SHORT).show();
            error = true;
        }
            mediaRecorder.release();
            mediaRecorder = null;

    }

    private void startRecording() {
        recordPath = this.getExternalFilesDir("/").getAbsolutePath();
        recordFile = "filename.mp3";

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(recordPath + "/" + recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

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
        myIntent.putExtra(KLUCZ_WIADOMOSCI2, recordFile);
        myIntent.putExtra(KLUCZ_WIADOMOSCI3, recordPath);
        MainActivity.this.startActivity(myIntent);
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






