package pollub.ism.praktyki;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class OurAdapter extends RecyclerView.Adapter<OurAdapter.OurHolder> {

    public static class DaneAdaptera {
        public final  String nazwa;

        public DaneAdaptera(String nazwa){
            this.nazwa = nazwa;
        }
    }

    public interface DataProvider{
        ArrayList<DaneAdaptera> getDane();
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
            DaneAdaptera danePozycji = dostawca.getDane().get(getLayoutPosition());

        }

    }

    //-------------------------------------------------------------------------------

    private final Context kontekst;
    private final DataProvider dostawca;
    private LayoutInflater inflater;

    public OurAdapter(Context kontekst, DataProvider dostawca){
        this.kontekst = kontekst;
        this.inflater = LayoutInflater.from(kontekst);
        this.dostawca = dostawca;
    }


    @NonNull
    @Override
    public OurHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pozycja = inflater.inflate(R.layout.pozycja_recycler_view, parent, false);
        return new OurHolder(pozycja,this);
    }

    @Override
    public void onBindViewHolder(@NonNull OurHolder holder, int position) {
        DaneAdaptera dane = dostawca.getDane().get(position);
        holder.nazwaLista.setText(dane.nazwa);
    }

    @Override
    public int getItemCount() {
        return dostawca.getDane().size();
    }

}
