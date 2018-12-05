package br.com.bossini.weatherforecastbycityfateccarapicuibamanha;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class DetailsMovieActivity extends AppCompatActivity {

    private TextView nomeOriginalTextView;
    private ImageView imagemImageView;
    private TextView detalheTextView;
    private TextView notaTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);
        Intent intent = getIntent();
        String idMovie = intent.getStringExtra(MoviesActivity.MOVIE);
        nomeOriginalTextView = findViewById(R.id.nomeOriginalTextView);
        detalheTextView = findViewById(R.id.detalheTextView);
        notaTextView = findViewById(R.id.nota2TextView);
        imagemImageView = findViewById(R.id.imagemImageView);
        StringBuilder sb = new StringBuilder();
        Locale currentLocale = Locale.getDefault();
        sb.append(currentLocale.getLanguage());
        sb.append("-");
        sb.append(currentLocale.getCountry());
        String chave = getString(R.string.api_key);
        String end =  getString(R.string.web_service_url_movie_selected,idMovie,chave,sb);
        new ObtemMovie().execute(end);

    }
    private class ObtemMovie extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                InputStream inputStream =
                        connection.getInputStream();
                InputStreamReader inputStreamReader =
                        new InputStreamReader(inputStream);
                BufferedReader reader =
                        new BufferedReader(inputStreamReader);
                String linha = null;
                StringBuilder sb = new StringBuilder();
                while ((linha = reader.readLine()) != null){
                    sb.append(linha);
                }
                reader.close();
                return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonS) {

            try {
                JSONObject json = new JSONObject(jsonS);
                //JSONArray results = json.getJSONArray("genres");
                //JSONObject movie = results.getJSONObject(i);

                String nomeOriginal = json.getString("title");
                String detalhes = json.getString("overview");
                String nota = json.getString("vote_average");

                String posterPath = json.getString("poster_path");
                String filepath = "https://image.tmdb.org/t/p/w500" + posterPath ;
                nomeOriginalTextView.setText(nomeOriginal);
                new BaixaImagem().execute(filepath);
                detalheTextView.setText(detalhes);
                notaTextView.setText(nota);
            }catch(JSONException e) {
                e.printStackTrace();
                //Toast.makeText(MoviesActivity.this,"catch",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class BaixaImagem extends AsyncTask <String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try{
                URL url = new URL (urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                Bitmap figura = BitmapFactory.decodeStream(inputStream);
                return figura;
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap figura) {
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(figura, 120, 200, false);
            imagemImageView.setImageBitmap(resizedBitmap);
        }
    }
}