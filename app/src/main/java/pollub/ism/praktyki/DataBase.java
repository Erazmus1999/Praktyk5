package pollub.ism.praktyki;

import android.media.MediaPlayer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class DataBase extends AppCompatActivity
{

    public void play_Song(View v)
    {
    MediaPlayer mediaPlayer = new MediaPlayer();

    try
    {
        mediaPlayer.setDataSource(""); //Tu podajemy URL

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                mp.start();
            }
        });
            mediaPlayer.prepare();
        }
    catch(IOException e)
        {
            e.printStackTrace();
        }

    }
}