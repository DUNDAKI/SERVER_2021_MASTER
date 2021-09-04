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
import android.widget.Toast;

import foo.fd.estudodecasolistar.model.ListarCidadeAsyncTask;
import foo.fd.estudodecasolistar.R;
import foo.fd.estudodecasolistar.model.ListarEstadoAsyncTask;

public class MainActivity extends AppCompatActivity {
    Button buscarCidade,buscarEstado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buscarCidade = (Button) findViewById(R.id.idCidade);
        buscarEstado = (Button) findViewById(R.id.idEstado);

        buscarCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListarCidadeAsyncTask task = new ListarCidadeAsyncTask("fabricadedesenvolvedor");
                task.execute();
                Log.i("APIListar","buscarCidade()");
            }
        });

        buscarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListarEstadoAsyncTask task2 = new ListarEstadoAsyncTask("fabricadedesenvolvedor");
                task2.execute();
                Log.i("APIListar","buscarEstado()");
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
