package foo.fd.estudodecasolistar.model;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

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
