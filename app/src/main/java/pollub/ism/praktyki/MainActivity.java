package pollub.ism.praktyki;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void runMain(View view){
        setContentView(R.layout.activity_main);
    }
    public void runRecord(View view){
        setContentView(R.layout.activity_record);
    }
    public void runSummary(View view){
        setContentView(R.layout.activity_summary);
    }
    public void runSearch(View view) {
        setContentView(R.layout.activity_search);
    }
    public void runResult(View view){
        setContentView(R.layout.activity_result);
    }
}