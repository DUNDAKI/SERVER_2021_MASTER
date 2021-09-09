package foo.fd.estudodecasolistar.model;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import foo.fd.estudodecasolistar.R;
import foo.fd.estudodecasolistar.utils.GetIp;

public class DeletarEstadoAsyncTask extends
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



    public DeletarEstadoAsyncTask(String token, int idCidade){

            this.api_token = token;
            this.builder = new Uri.Builder();
            builder.appendQueryParameter("api_token", api_token);
            builder.appendQueryParameter("api_idCidade", String.valueOf(idCidade));

        }

        @Override
        protected void onPreExecute () {

            Log.i("APIListar", "onPreExecute()");

        }

        @Override
        protected String doInBackground (String...strings){

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
        protected void onPostExecute (String result){ // Objeto Json

            Log.i("APIListar", "onPostExecute()--> Result: " + result);

            try {

                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.getBoolean("deletado")) {

                    txtResultado.setText("Registro Deletado: " + idCidade);

                    Log.i("APIListar", "onPostExecute()--> Deletado com Sucesso");

                } else {

                    txtResultado.setText("Falha ao Deletar: " + idCidade);

                    Log.i("APIListar", "onPostExecute()--> Falha ao Deletar");
                    Log.i("APIListar", "onPostExecute()--> " + jsonObject.getString("SQL"));

                }


            } catch (Exception e) {

                Log.i("APIListar", "onPostExecute()--> " + e.getMessage());
            }

        }
    }
