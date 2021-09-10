package foo.fd.estudodecasolistar.view;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import foo.fd.estudodecasolistar.controller.ControllerCidade;
import foo.fd.estudodecasolistar.model.Cidade;
import foo.fd.estudodecasolistar.model.ListarCidadeAsyncTask;
import foo.fd.estudodecasolistar.R;
import foo.fd.estudodecasolistar.model.ListarEstadoAsyncTask;
import foo.fd.estudodecasolistar.utils.GetIp;

public class MainActivity extends AppCompatActivity {
    Button btnBuscarEstado;
    Button btnBuscarCidade;
    Button btnPesquisarCidade;
    Button btnDeletarCidade;
    Button btnAlterarCidade;

    EditText deleteIdCidade;
    EditText deleteEstado;
    EditText editNomeCidade;
    TextView txtResultado1;

    ControllerCidade  controllerCidade;
    List<Cidade> cidadeList;
    Cidade obj;
    Integer idCidade;
    String token = "fabricadedesenvolvedor";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnBuscarCidade = (Button) findViewById(R.id.btnListarCidades);
        btnBuscarEstado = (Button) findViewById(R.id.btnListarEstados);
        btnPesquisarCidade = (Button) findViewById(R.id.btnPesquisarCidade);
//        btnAlterarCidade = (Button) findViewById(R.id.btnDeletarCidade);
        btnDeletarCidade = (Button) findViewById(R.id.btnDeletarCidade);
        deleteIdCidade =  (EditText)  findViewById(R.id.editCidade);
        deleteEstado =  (EditText) findViewById(R.id.editCidade);
        txtResultado1 = (TextView) findViewById(R.id.txtResultadoCidade);

//

        btnBuscarCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListarCidadeAsyncTask task = new ListarCidadeAsyncTask(token);
                task.execute();
                Log.i("APIListar","buscarCidade()");
            }
        });

        btnBuscarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListarEstadoAsyncTask task2 = new ListarEstadoAsyncTask(token);
                task2.execute();
                Log.i("APIListar","buscarEstado()");
            }
        });

        btnDeletarCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnDeletarCidade != null){
                    idCidade = Integer.parseInt(deleteIdCidade.getText().toString());
                    Log.i("APIListar","btnDeletarCidade():  " + idCidade);

//                    DeletarCidadeAsyncTask task = new DeletarCidadeAsyncTask(token, idCidade);
//                    task.execute();

                    DeletarCidadeAsyncTask2 task2 = new DeletarCidadeAsyncTask2(token, idCidade);
                    task2.execute();

                }else{
                    txtResultado1.setText("Informe ID da cidade");
                }

            }
        });


        btnPesquisarCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });






        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public class DeletarCidadeAsyncTask2
            extends
            AsyncTask<String, String, String> {
        TextView txtResultado;


        int idCidade;

        String api_token, query;

        HttpURLConnection conn;
        URL url = null;
        Uri.Builder builder;

        GetIp getIp = new GetIp();
        String ip3 = getIp.getDeleteCidade();

        final String URL_WEB_SERVICES = ip3;

        final int READ_TIMEOUT = 10000;
        final int CONNECTION_TIMEOUT = 30000;

        int response_code;


        public DeletarCidadeAsyncTask2(String token, int idCidade) {

            this.api_token = token;
            this.builder = new Uri.Builder();
            builder.appendQueryParameter("api_token", api_token);
            builder.appendQueryParameter("api_idCidade", String.valueOf(idCidade));

        }

        @Override
        protected void onPreExecute() {

            Log.i("APIListar", "onPreExecute()");

        }

        @Override
        protected String doInBackground(String... strings) {

            Log.i("APIListar", "doInBackground()");

            // Gerar o conteúdo para a URL

            try {

                url = new URL(URL_WEB_SERVICES);

            } catch (MalformedURLException e) {

                Log.i("APIListar", "MalformedURLException --> " + e.getMessage());

            } catch (Exception e) {

                Log.i("APIListar", "doInBackground() --> " + e.getMessage());
            }

            // Gerar uma requisição HTTP - POST - Result será um ArrayJson

            // conn

            try {

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("charset", "utf-8");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.connect();

            } catch (Exception e) {

                Log.i("APIListar", "HttpURLConnection --> " + e.getMessage());

            }

            // Adicionar o TOKEN e/ou outros parâmetros como por exemplo
            // um objeto a ser incluido, deletado ou alterado.
            // CRUD completo

            try {

                query = builder.build().getEncodedQuery();

                OutputStream stream = conn.getOutputStream();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(stream, "utf-8"));

                writer.write(query);
                writer.flush();
                writer.close();
                stream.close();

                conn.connect();

            } catch (Exception e) {

                Log.i("APIListar", "BufferedWriter --> " + e.getMessage());

            }

            // receber o response - arrayJson
            // http - código do response | 200 | 404 | 503

            try {

                response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {


                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(

                            new InputStreamReader(input)

                    );

                    StringBuilder result = new StringBuilder();

                    String linha = null;

                    while ((linha = reader.readLine()) != null) {

                        result.append(linha);
                    }

                    return result.toString();

                } else {

                    return "HTTP ERRO: " + response_code;
                }

            } catch (Exception e) {

                Log.i("APIListar", "StringBuilder --> " + e.getMessage());

                return "Exception Erro: " + e.getMessage();

            } finally {

                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String result) { // Objeto Json

            Log.i("APIListar", "onPostExecute()--> Result: " + result);

            try {
                idCidade = Integer.parseInt(deleteIdCidade.getText().toString());
                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.getBoolean("deletado")) {

                    txtResultado1.setText("Registro Deletado: "+idCidade);


                    Log.i("APIListar", "onPostExecute()--> Deletado com Sucesso");

                }else{

                    txtResultado1.setText("Falha ao Deletar: "+idCidade);

                    Log.i("APIListar", "onPostExecute()--> Falha ao Deletar");
                    Log.i("APIListar", "onPostExecute()--> "+jsonObject.getString("SQL"));

                }



            }catch (Exception e){

                Log.i("APIListar", "onPostExecute()--> " + e.getMessage());
            }

        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
