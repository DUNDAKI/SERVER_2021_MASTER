package foo.fd.estudodecasolistar.view;

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

import java.util.List;

import foo.fd.estudodecasolistar.controller.ControllerCidade;
import foo.fd.estudodecasolistar.model.Cidade;
import foo.fd.estudodecasolistar.model.DeletarCidadeAsyncTask;
import foo.fd.estudodecasolistar.model.DeletarEstadoAsyncTask;
import foo.fd.estudodecasolistar.model.ListarCidadeAsyncTask;
import foo.fd.estudodecasolistar.R;
import foo.fd.estudodecasolistar.model.ListarEstadoAsyncTask;

public class MainActivity extends AppCompatActivity {
    Button btnBuscarEstado;
    Button btnBuscarCidade;
    Button btnPesquisarCidade;
    Button btnDeletarCidade;
    Button btnAlterarCidade;

    EditText deleteCidade;
    EditText deleteEstado;
    EditText editNomeCidade;
    TextView txtResultado;

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
        deleteCidade =  (EditText)  findViewById(R.id.editCidade);
        deleteEstado =  (EditText) findViewById(R.id.editCidade);
        txtResultado = (TextView) findViewById(R.id.txtResultado);

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
                    idCidade = Integer.parseInt(deleteCidade.getText().toString());
                    Log.i("APIListar","btnDeletarCidade():  " + idCidade);

                    DeletarCidadeAsyncTask task = new DeletarCidadeAsyncTask(token, idCidade);
                    task.execute();

                }else{
                    txtResultado.setText("Informe ID da cidade");
                }

            }
        });

        deleteEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                idCidade = Integer.parseInt(deleteCidade.getText().toString());
                Log.i("APIListar","btnDeletarCidade():  " + idCidade);

                DeletarEstadoAsyncTask task = new DeletarEstadoAsyncTask(token, idCidade);
                task.execute();

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
