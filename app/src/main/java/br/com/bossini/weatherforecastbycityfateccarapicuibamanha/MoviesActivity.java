package br.com.bossini.weatherforecastbycityfateccarapicuibamanha;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MoviesActivity extends AppCompatActivity{

    public static final String MOVIE = "Movie";
    private List<Movie> movies;
    private ListView moviesListView;
    private MovieAdapter adapter;
    private EditText editsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        Intent intent = getIntent();
        String idGenero = intent.getStringExtra(MainActivity.GENRE);
        movies = new ArrayList<>();
        moviesListView = findViewById(R.id.moviesListView);
        adapter = new MovieAdapter( this, movies);
        moviesListView.setAdapter(adapter);
        StringBuilder sb = new StringBuilder();
        Locale currentLocale = Locale.getDefault();
        sb.append(currentLocale.getLanguage());
        sb.append("-");
        sb.append(currentLocale.getCountry());
        String chave = getString(R.string.api_key);
        String end =  getString(R.string.web_service_url_list_of_selected_genres,chave,idGenero,sb,"1");
        new ObtemMovies().execute(end);
    }

    private class ObtemMovies extends AsyncTask<String, Void, String> {

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

           movies.clear();

            try {
                JSONObject json = new JSONObject(jsonS);
                JSONArray results = json.getJSONArray("results");
                for (int i = 0; i < results.length(); i++){
                    JSONObject movie = results.getJSONObject(i);
                    String id = movie.getString("id");
                    String nome = movie.getString("title");
                    Movie m = new Movie(id,nome);
                    movies.add(m);
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(MoviesActivity.this,"catch",Toast.LENGTH_SHORT).show();
            }
        }
    }

}




