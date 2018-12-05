package br.com.bossini.weatherforecastbycityfateccarapicuibamanha;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreAdapter extends ArrayAdapter <Genre> {

    private Context context;
    public GenreAdapter(Context context, List <Genre> generos){
        super(context, -1, generos);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Genre caraDaVez = getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = convertView.findViewById(R.id.nameTextView);
            viewHolder.buttonGenre = convertView.findViewById(R.id.buttonGenre);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        switch(caraDaVez.id){
            case "28":   viewHolder.buttonGenre.setImageResource(R.drawable.acao);break;
            case "12":   viewHolder.buttonGenre.setImageResource(R.drawable.aventura);break;
            case "16":   viewHolder.buttonGenre.setImageResource(R.drawable.animacao);break;
            case "35":   viewHolder.buttonGenre.setImageResource(R.drawable.comedia);break;
            case "80":   viewHolder.buttonGenre.setImageResource(R.drawable.crime);break;
            case "99":   viewHolder.buttonGenre.setImageResource(R.drawable.documentario);break;
            case "18":   viewHolder.buttonGenre.setImageResource(R.drawable.drama);break;
            case "10751":   viewHolder.buttonGenre.setImageResource(R.drawable.familia);break;
            case "14":   viewHolder.buttonGenre.setImageResource(R.drawable.fantasia);break;
            case "36":   viewHolder.buttonGenre.setImageResource(R.drawable.historia);break;
            case "27":   viewHolder.buttonGenre.setImageResource(R.drawable.terror);break;
            case "10402":   viewHolder.buttonGenre.setImageResource(R.drawable.musica);break;
            case "9648":   viewHolder.buttonGenre.setImageResource(R.drawable.misterio);break;
            case "10749":   viewHolder.buttonGenre.setImageResource(R.drawable.romance);break;
            case "878":   viewHolder.buttonGenre.setImageResource(R.drawable.ficcao_cientifica);break;
            case "10770":   viewHolder.buttonGenre.setImageResource(R.drawable.cinema_tv);break;
            case "53":   viewHolder.buttonGenre.setImageResource(R.drawable.thriller);break;
            case "10752":   viewHolder.buttonGenre.setImageResource(R.drawable.guerra);break;
            case "37":   viewHolder.buttonGenre.setImageResource(R.drawable.faroeste);break;

        }
        viewHolder.nameTextView.setText(context.getString(R.string.name, caraDaVez.nome));

        viewHolder.buttonGenre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(context, MoviesActivity.class);
                it.putExtra(MainActivity.GENRE,caraDaVez.id);
                context.startActivity(it);
            }
        });
        return convertView;
    }

    private class ViewHolder{
        public TextView nameTextView;
        public ImageButton buttonGenre;

    }
}