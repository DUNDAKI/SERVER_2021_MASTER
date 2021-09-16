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

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;

import foo.fd.estudodecasolistar.controller.ControllerCidade;

import foo.fd.estudodecasolistar.controller.MODEL2.Cidade;
import foo.fd.estudodecasolistar.controller.MODEL2.Estado;
import foo.fd.estudodecasolistar.R;
import foo.fd.estudodecasolistar.utils.GetIp;

public class MainActivity extends AppCompatActivity {
    Button btnBuscarEstado;
    Button btnBuscarCidade;
    Button btnPesquisar;
    Button btnDeletarCidade;
    Button btnAlterarCidade;

    EditText editIDCidade;
    EditText deleteEstado;
    EditText editNomeCidade;
    TextView txtResultado;

    ControllerCidade  controllerCidade;

    List<Cidade> cidadesList;
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
        btnPesquisar = (Button) findViewById(R.id.btnPesquisarCidade);
        btnDeletarCidade = (Button) findViewById(R.id.btnDeletarCidade);

        editIDCidade =  (EditText)  findViewById(R.id.editIdCidade);

        deleteEstado =  (EditText) findViewById(R.id.editIdCidade);
        txtResultado = (TextView) findViewById(R.id.txtResultadoCidade);

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

                ListarEstadoAsyncTask task = new ListarEstadoAsyncTask(token);
                task.execute();
                Log.i("APIListar","buscarEstado()");
            }
        });

        btnDeletarCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnDeletarCidade != null){
                    idCidade = Integer.parseInt(editIDCidade.getText().toString());
                    Log.i("APIListar","btnDeletarCidade():  " + idCidade);

                    DeletarCidadeAsyncTask2 task2 = new DeletarCidadeAsyncTask2(token, idCidade);
                    task2.execute();

                }else{
                    txtResultado.setText("Informe ID da cidade");
                }

            }
        });



        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obj = new Cidade();
                controllerCidade = new ControllerCidade();

                // Não estamos validadando as entrada de dados.

                obj = controllerCidade.buscarObjeto(cidadesList,editNomeCidade.getText().toString());

                try {

                    txtResultado.setText("Nome " + obj.getNome());

                    editIDCidade.setText(String.valueOf(obj.getId()));

                }catch (Exception e){

                    txtResultado.setText("Cidade Não localizada...");

                }


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

    public class DeletarCidadeAsyncTask2 extends AsyncTask<String, String, String> {
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
                idCidade = Integer.parseInt(editIDCidade.getText().toString());
                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.getBoolean("deletado")) {

                    txtResultado.setText("Registro Deletado: "+idCidade);


                    Log.i("APIListar", "onPostExecute()--> Deletado com Sucesso");

                }else{

                    txtResultado.setText("Falha ao Deletar: "+idCidade);

                    Log.i("APIListar", "onPostExecute()--> Falha ao Deletar");
                    Log.i("APIListar", "onPostExecute()--> "+jsonObject.getString("SQL"));

                }



            }catch (Exception e){

                Log.i("APIListar", "onPostExecute()--> " + e.getMessage());
            }

        }
    }

    public class ListarCidadeAsyncTask extends AsyncTask<String, String, String> {

        private Button getCidade, getEstado;

        GetIp getIp = new GetIp();
        String ip3 = getIp.getListarCidade();

        String query;
        HttpURLConnection conn;
        URL url = null;
        Uri.Builder builder;
        int response_code;
        final  int READ_TIME_OUT = 10000;
        final  int CONNECTION_TIME_OUT = 30000;

        final String URL_WEB_SERVICE = ip3;

        String api_token;
        public ListarCidadeAsyncTask(String token){
            this.api_token = token;
            this.builder = new Uri.Builder();
            builder.appendQueryParameter("api_token", api_token);

        }

        @Override
        protected void onPreExecute(){
            Log.i("APIListar","onPreExecute()");

        }
        @Override
        protected String doInBackground(String... strings) {

            Log.i("APIListar","doInBackground()");
            // Gerar o conteúdo para a URL
            try {
                url = new URL(URL_WEB_SERVICE);

            }catch (MalformedURLException e){
                Log.i("APIListar","doInBackground() --> "+e.getMessage());
            }catch (Exception e){
                Log.i("APIListar","doInBackground() --> "+e.getMessage());
            }

            // Gerar uma requisição HTTP - POST - Result será um ArrayJson
            // conn
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIME_OUT);
                conn.setConnectTimeout(CONNECTION_TIME_OUT);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("charset","utf-8");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.connect();

            }catch (Exception e){

                Log.i("APIListar","doInBackground() --> "+e.getMessage());

            }

            // Adicionar o TOKEN e/ou outros parâmetros como por exemplo
            // um objeto a ser incluido, deletado ou alterado.
            // CRUD completo
            try {

                query = builder.build().getEncodedQuery();

                OutputStream stream = conn.getOutputStream();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(stream,"utf-8"));

                writer.write(query);
                writer.flush();
                writer.close();
                stream.close();

                conn.connect();



            }catch (Exception e){

                Log.i("APIListar","doInBackground() --> "+e.getMessage());


            }

            // receber o response - arrayJson
            // http - código do response | 200 | 404 | 503

            try {
                response_code = conn.getResponseCode();

                if(response_code == HttpURLConnection.HTTP_OK){

                    InputStream input = conn.getInputStream();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(input)
                    );
                    StringBuilder result = new StringBuilder();
                    String linha = null;

                    while((linha = reader.readLine()) != null){
                        result.append(linha);
                    }
                    //RETORNA LISTA
                    return result.toString();

                }else{
                    return "HTTP ERRO ==> " +response_code;
                }

            }catch (Exception e){

                Log.i("APIListar","doInBackground() --> "+e.getMessage());
            }finally {
                //FECHA O SERVIDOR
                conn.disconnect();
            }
            return "Processamento com sucesso...";


        }

        @Override
        protected void onPostExecute(String result){

            Log.i("APIListar","onPostExecute()--> Result: "+result);
            Cidade cidade;
            try{
                JSONArray jsonArray = new JSONArray(result);
                if(jsonArray.length() != 0 ){

                    cidadesList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length() ; i++ ){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        cidade = new Cidade(jsonObject.getInt("id"),
                                jsonObject.getString("cidade"),
                                jsonObject.getString("estado"));

                        cidadesList.add(cidade);

                        Log.i("APIListar", "Cidade ==> " + cidade.getId() + " - "
                                + cidade.getNome() + " - " + cidade.getSigla());
                    }
                }
            }catch (Exception e ){
                Log.i("APIListar","doInBackground() --> "+e.getMessage());
            }


        }




    }

    public class ListarEstadoAsyncTask extends AsyncTask<String, String, String> {

        private Button getCidade, getEstado;

        String ip = "10.0.0.104";
        String ip2 = "192.168.1.108";
        String  caminho = "http://"+ip2+"/curso_udemy/exer/ApiListarEstados.php";
        String query;
        HttpURLConnection conn;
        URL url = null;
        Uri.Builder builder;
        int response_code;
        final  int READ_TIME_OUT = 10000;
        final  int CONNECTION_TIME_OUT = 30000;

        final String URL_WEB_SERVICE = caminho;

        String api_token;
        public ListarEstadoAsyncTask(String token){
            this.api_token = token;
            this.builder = new Uri.Builder();
            builder.appendQueryParameter("api_token", api_token);

        }

        @Override
        protected void onPreExecute(){
            Log.i("APIListar","onPreExecute()");

        }
        @Override
        protected String doInBackground(String... strings) {

            Log.i("APIListar","doInBackground()");
            // Gerar o conteúdo para a URL
            try {
                url = new URL(URL_WEB_SERVICE);

            }catch (MalformedURLException e){
                Log.i("APIListar","doInBackground() --> "+e.getMessage());
            }catch (Exception e){
                Log.i("APIListar","doInBackground() --> "+e.getMessage());
            }

            // Gerar uma requisição HTTP - POST - Result será um ArrayJson
            // conn
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIME_OUT);
                conn.setConnectTimeout(CONNECTION_TIME_OUT);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("charset","utf-8");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.connect();

            }catch (Exception e){

                Log.i("APIListar","doInBackground() --> "+e.getMessage());

            }

            // Adicionar o TOKEN e/ou outros parâmetros como por exemplo
            // um objeto a ser incluido, deletado ou alterado.
            // CRUD completo
            try {

                query = builder.build().getEncodedQuery();

                OutputStream stream = conn.getOutputStream();

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(stream,"utf-8"));

                writer.write(query);
                writer.flush();
                writer.close();
                stream.close();

                conn.connect();



            }catch (Exception e){

                Log.i("APIListar","doInBackground() --> "+e.getMessage());


            }

            // receber o response - arrayJson
            // http - código do response | 200 | 404 | 503

            try {
                response_code = conn.getResponseCode();

                if(response_code == HttpURLConnection.HTTP_OK){

                    InputStream input = conn.getInputStream();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(input)
                    );
                    StringBuilder result = new StringBuilder();
                    String linha = null;

                    while((linha = reader.readLine()) != null){
                        result.append(linha);
                    }
                    //RETORNA LISTA
                    return result.toString();

                }else{
                    return "HTTP ERRO ==> " +response_code;
                }

            }catch (Exception e){

                Log.i("APIListar","doInBackground() --> "+e.getMessage());
            }finally {
                //FECHA O SERVIDOR
                conn.disconnect();
            }
            return "Processamento com sucesso...";


        }

        @Override
        protected void onPostExecute(String result){

            Log.i("APIListar","onPostExecute()--> Result: "+result);
            Estado estado;
            try{
                JSONArray jsonArray = new JSONArray(result);
                if(jsonArray.length() != 0 ){
                    for (int i = 0; i < jsonArray.length() ; i++ ){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        estado = new Estado(jsonObject.getInt("id"),
                                jsonObject.getString("nome"),
                                jsonObject.getString("sigla"));

                        Log.i("APIListar", "Estado ==> " + estado.getId() + " - "
                                + estado.getNome() + " - " + estado.getSigla());
                    }
                }
            }catch (Exception e ){
                Log.i("APIListar","doInBackground() --> "+e.getMessage());
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
