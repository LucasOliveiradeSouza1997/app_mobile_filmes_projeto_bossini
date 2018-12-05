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

public class MovieAdapter extends ArrayAdapter <Movie> {

    private Context context;
    public MovieAdapter(Context context, List <Movie> movies){
        super(context, -1, movies);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie caraDaVez = getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_movies, parent, false);
            viewHolder = new ViewHolder();
            //viewHolder.idTextView = convertView.findViewById(R.id.idMovieTextView);
            viewHolder.nameTextView = convertView.findViewById(R.id.nameMovieTextView);
            viewHolder.buttonMovie = convertView.findViewById(R.id.buttonMovie);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        //viewHolder.idTextView.setText(context.getString(R.string.idMovie, caraDaVez.id));
        viewHolder.nameTextView.setText(context.
                getString(R.string.nameMovie, caraDaVez.nome));
        viewHolder.buttonMovie.setText(context.getString(R.string.button_movie));

        viewHolder.buttonMovie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(context, DetailsMovieActivity.class);
                it.putExtra(MoviesActivity.MOVIE,caraDaVez.id);
                context.startActivity(it);
            }
        });
        return convertView;
    }

    private class ViewHolder{
       // public TextView idTextView;
        public TextView nameTextView;
        public Button buttonMovie;

    }
}