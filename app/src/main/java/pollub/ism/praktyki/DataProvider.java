package pollub.ism.praktyki;


import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DataProvider implements OurAdapter.DataProvider{

    ArrayList<OurAdapter.DaneAdaptera> dane = new ArrayList<>();

    /*@Override
    public ArrayList<OurAdapter.DaneAdaptera> getDane() {
        return dane;
    }*/

    public DataProvider(StorageReference database, OurAdapter a) {
        ArrayList<String> mylist = new ArrayList<>();
        StorageReference x = database;

        x.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference prefix : listResult.getPrefixes()) {
                    mylist.add(prefix.getName());
                }
                for (StorageReference item : listResult.getItems()) {
                    mylist.add(item.getName());
                }
                for(int i=0; i<mylist.size(); i++){
                    dane.add(new OurAdapter.DaneAdaptera(mylist.get(i)));
                }
                a.setDane(dane);
            }

        });



    }
}