package pollub.ism.praktyki;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DataProvider implements OurAdapter.DataProvider{

    ArrayList<OurAdapter.DaneAdaptera> dane = new ArrayList<>();

    @Override
    public ArrayList<OurAdapter.DaneAdaptera> getDane() {
        return dane;
    }

    public DataProvider(StorageReference database) {
        ArrayList<String> mylist = new ArrayList<>();
        StorageReference x = database;
        x.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference prefix : listResult.getPrefixes()) {
                    mylist.add(prefix.getName());
                }
                for(int i=0; i<mylist.size(); i++){
                    dane.add(new OurAdapter.DaneAdaptera(mylist.get(i)));
                }
            }
        });

    }
}