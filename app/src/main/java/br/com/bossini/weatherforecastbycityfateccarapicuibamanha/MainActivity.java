package br.com.bossini.weatherforecastbycityfateccarapicuibamanha;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity {

    public static final String GENRE = "Genre";

    private List<Genre> generos;
    private ListView genreListView;
    private GenreAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        generos = new ArrayList<>() ;
        genreListView = findViewById(R.id.genreListView);
        adapter = new GenreAdapter(this, generos);
        genreListView.setAdapter(adapter);
        String chave = getString(R.string.api_key);
        StringBuilder sb = new StringBuilder();
        Locale currentLocale = Locale.getDefault();
        sb.append(currentLocale.getLanguage());
        sb.append("-");
        sb.append(currentLocale.getCountry());
        String end = getString(R.string.web_service_url_list_of_genres,chave,sb);
        new ObtemGeneros().execute(end);
    }

    private class ObtemGeneros extends AsyncTask <String, Void, String>{

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
        protected void onPostExecute(String jsonS) { ;
          /*  Toast.makeText(MainActivity.this,
                    jsonS, Toast.LENGTH_SHORT).show();*/
            generos.clear();
            try {
                JSONObject json = new JSONObject(jsonS);
                JSONArray genres = json.getJSONArray("genres");
                for (int i = 0; i < genres.length(); i++){

                    JSONObject genero = genres.getJSONObject(i);
                    String id = genero.getString("id");
                    String nome = genero.getString("name");
                    Genre g = new Genre(id,nome);
                    generos.add(g);
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
