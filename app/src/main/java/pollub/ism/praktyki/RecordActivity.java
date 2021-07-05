package pollub.ism.praktyki;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class RecordActivity extends AppCompatActivity {

    private ImageButton b1;
    private boolean currentlyRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        b1 = findViewById(R.id.record_mic);
    }

    public void runMain(View view){
        setContentView(R.layout.activity_main);
    }

    public void runSummary(View view){
        setContentView(R.layout.activity_summary);
    }

    public void recordSound(View view){
        setContentView(R.layout.activity_main);
    }

}