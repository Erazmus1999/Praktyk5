package pollub.ism.praktyki;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;import pollub.ism.praktyki.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private String newName;
    private TextView h;
    private String path, file, category;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private ActivityMain2Binding binding;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = ArrayAdapter.createFromResource(this, R.array.Asortyment, android.R.layout.simple_dropdown_item_1line);
        binding.spinner.setAdapter(adapter);



        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);

        //przekazanie czasu nagrania przez pobranie intencji
        Intent myIntent = getIntent();
        CharSequence wiadomosc = myIntent.getCharSequenceExtra(MainActivity.KLUCZ_WIADOMOSCI);
        file = myIntent.getStringExtra(MainActivity.KLUCZ_WIADOMOSCI2);
        path = myIntent.getStringExtra(MainActivity.KLUCZ_WIADOMOSCI3);
        TextView x = findViewById(R.id.czas);
        x.setText(wiadomosc);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Anulowanie Nagrania")
                .setMessage("Czy chcesz anulować nagranie?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File a = new File(path, file);
                        if (a.exists())
                            a.delete();
                        finish();
                    }

                })
                .setNegativeButton("Nie", null)
                .show();
    }

    public void wyslij(View view)
    {
        h = findViewById(R.id.nazwaPliku);
        newName = h.getText().toString();
        newName = newName.trim();

        if (newName.length() >=1)
        {
            newName += ".mp3";
            File f = new File(path, file);
            File d = new File(path, newName);
            if(d.exists())
            {
                Toast.makeText(this, "Podana nazwa pliku jest juz uzywana!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (f.renameTo(d)) {
                    Toast.makeText(this, "Pomyślnie utworzono plik!", Toast.LENGTH_SHORT).show();
                    uploadAudio();
                } else {
                    Toast.makeText(this, "Nie udało się utworzyć pliku!", Toast.LENGTH_SHORT).show();

                }
                finish();
            }
        }
        else
        {
            Toast.makeText(this, "Nie wpisano nazwy nagrania!", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadAudio()
    {
        category = binding.spinner.getSelectedItem().toString();

        StorageReference filepath = mStorage.child("Audio").child(category).child(newName);

        Uri uri = Uri.fromFile(new File(path, newName));
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mProgress.dismiss();
                Toast.makeText(MainActivity2.this, "Uploading finished!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}