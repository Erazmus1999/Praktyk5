package pollub.ism.praktyki;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;


public class OurAdapter extends RecyclerView.Adapter<OurAdapter.OurHolder> {
    public final String TAG = "XD";
    public ArrayList<DaneAdaptera> daneAdaptera;
    public String helper;
    public static class DaneAdaptera {
        public final  String nazwa;

        public DaneAdaptera(String nazwa){
            this.nazwa = nazwa;
        }
    }

    public interface DataProvider{

        //ArrayList<DaneAdaptera> getDane();
    }

    public void setDane(ArrayList<DaneAdaptera> a)
    {
        daneAdaptera = a;
        notifyDataSetChanged();
    }

    class OurHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final OurAdapter adapter;
        public final TextView nazwaLista;

        public OurHolder(@NonNull View itemView, OurAdapter adapter) {
            super(itemView);
            nazwaLista = itemView.findViewById(R.id.nazwaLista);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

       @Override
        public void onClick(View view) {
            DaneAdaptera danePozycji = daneAdaptera.get(getLayoutPosition());
            if (danePozycji.nazwa.equals("Samochody") || danePozycji.nazwa.equals("Odgłosy zwierząt")  || danePozycji.nazwa.equals("Inne") || danePozycji.nazwa.equals("Odgłosy natury") || danePozycji.nazwa.equals("Dźwięki kuchenne") || danePozycji.nazwa.equals("Rozmowa") ) {
                helper = danePozycji.nazwa;
                pollub.ism.praktyki.DataProvider dostawca = new pollub.ism.praktyki.DataProvider(FirebaseStorage.getInstance().getReference().child("Audio").child(danePozycji.nazwa), adapter);
            }
            else
            {
                StorageReference storage = FirebaseStorage.getInstance().getReference();
                StorageReference storageRef = storage.child("Audio").child(helper).child(danePozycji.nazwa);
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(uri.toString()); //URL
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.start();
                                }
                            });

                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }

    }

    //-------------------------------------------------------------------------------

    private final Context kontekst;
    private LayoutInflater inflater;

    public OurAdapter(Context kontekst){
        this.kontekst = kontekst;
        this.inflater = LayoutInflater.from(kontekst);

    }


    @NonNull
    @Override
    public OurHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pozycja = inflater.inflate(R.layout.pozycja_recycler_view, parent, false);
        return new OurHolder(pozycja,this);
    }

    @Override
    public void onBindViewHolder(@NonNull OurHolder holder, int position) {
        DaneAdaptera dane = daneAdaptera.get(position);
        holder.nazwaLista.setText(dane.nazwa);
    }

    @Override
    public int getItemCount() {
        if (daneAdaptera == null)
            return 0;
        else
            return daneAdaptera.size();
    }

}
