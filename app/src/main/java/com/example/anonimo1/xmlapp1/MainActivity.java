package com.example.anonimo1.xmlapp1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Javier Benítez del Pozo on 11/03/2016.
 */
public class MainActivity extends AppCompatActivity {

    TextView tvParser;
    List<Noticia> noticias;
    Button xmlParser;
    boolean banderaMostrar;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvParser = (TextView)findViewById(R.id.textViewParser);
        xmlParser = (Button)findViewById(R.id.buttonParser);

        xmlParser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!banderaMostrar) {
                    //Carga del XML mediante la tarea asíncrona
                    xmlParser.setText("Borrar contenido");
                    CargarXmlTask tarea = new CargarXmlTask();
                    tarea.execute("http://www.europapress.es/rss/rss.aspx");
                    banderaMostrar = true;
                }else {
                    banderaMostrar = false;
                    xmlParser.setText("Mostrar XML parseado");
                    tvParser.setText("Aquí va el contenido xml parseado");
                }
            }
        });

    }

    //Tarea Asíncrona para cargar un XML en segundo plano
    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {

            RssParserSax saxparser =
                    new RssParserSax(params[0]);

            noticias = saxparser.parse();

            return true;
        }

        protected void onPostExecute(Boolean result) {

            //Tratamos la lista de noticias
            //Por ejemplo: escribimos los títulos en pantalla
            tvParser.setText("");
            for(int i=0; i<noticias.size(); i++)
            {
                tvParser.setText(
                        tvParser.getText().toString() +
                                System.getProperty("line.separator") +
                                noticias.get(i).getTitulo());
            }
        }
    }
}
