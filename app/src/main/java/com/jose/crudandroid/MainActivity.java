package com.jose.crudandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button novo = ((Button) findViewById(R.id.botaoNovo));
        novo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                abrirTelaCadastro();
            }
        });

        Button consultar = ((Button) findViewById(R.id.botaoConsultar));
        consultar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                consultarUsuarios();
            }
        });

        final ListView lista = ((ListView) findViewById(R.id.listView));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = ((Usuario) lista.getItemAtPosition(position));
                abrirTelaEdicao(usuario);
            }
        });
    }

    private void abrirTelaCadastro() {
        Intent intent = new Intent(MainActivity.this, SegundaTela.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", "");

        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void abrirTelaEdicao(Usuario usuario) {
        Intent intent = new Intent(MainActivity.this, SegundaTela.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", usuario.getId().toString());

        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void consultarUsuarios() {
        try {
            UsuarioDao usuarioDao = new UsuarioDao(this);

            List<Usuario> usuarioList = usuarioDao.consultar(((EditText) findViewById(R.id.campoNomeConsulta)).getText().toString());

            ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usuarioList);

            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
