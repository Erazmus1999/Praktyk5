package pollub.ism.praktyki;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //przekazanie czasu nagrania przez pobranie intencji
        Intent intencja = getIntent();
        CharSequence wiadomosc = intencja.getCharSequenceExtra(MainActivity.KLUCZ_WIADOMOSCI);
        TextView x = findViewById(R.id.czas);
        x.setText(wiadomosc);
    }
}