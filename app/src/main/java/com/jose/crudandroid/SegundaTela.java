package com.jose.crudandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SegundaTela extends AppCompatActivity {

    private Long id = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segunda_tela);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        String idString = bundle.getString("id");
        if (idString != null && !idString.isEmpty()) {
            id = Long.valueOf(idString);
        }

        Button cancelar = ((Button) findViewById(R.id.botaoCancelar));
        cancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                abrirTelaConsulta();
            }
        });

        Button cadastrar = ((Button) findViewById(R.id.botaoCadastrar));
        cadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (id == null) {
                    cadastrarUsuario();
                } else {
                    editarUsuario();
                }
                Intent intent = new Intent(SegundaTela.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (id != null) {
            UsuarioDao usuarioDao = new UsuarioDao(this);
            Usuario usuario = usuarioDao.usuarioPorId(id);

            EditText nome = ((EditText) findViewById(R.id.campoNome));
            nome.setText(usuario.getNome());

            EditText telefone = ((EditText) findViewById(R.id.campoTelefone));
            telefone.setText(usuario.getTelefone());

            EditText email = ((EditText) findViewById(R.id.campoEmail));
            email.setText(usuario.getEmail());

            EditText cpf = ((EditText) findViewById(R.id.campoCpf));
            cpf.setText(usuario.getCpf());

            Button botaoDeletar = ((Button) findViewById(R.id.botaoDeletar));
            botaoDeletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletarUsuario();
                }
            });
        } else {
            Button botaoDeletar = ((Button) findViewById(R.id.botaoDeletar));
            botaoDeletar.setEnabled(false);
        }
    }

    public void abrirTelaConsulta() {
        Intent intent = new Intent(SegundaTela.this, MainActivity.class);
        startActivity(intent);
    }

    public void cadastrarUsuario() {
        try {
            Usuario usuario = criaObjetoUsuario();

            UsuarioDao usuarioDao = new UsuarioDao(this);
            usuarioDao.cadastrar(usuario);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void editarUsuario() {
        Usuario usuario = criaObjetoUsuario();
        usuario.setId(id);

        UsuarioDao usuarioDao = new UsuarioDao(this);
        usuarioDao.editar(usuario);
    }

    private void deletarUsuario() {
        UsuarioDao usuarioDao = new UsuarioDao(this);

        Usuario usuario = usuarioDao.usuarioPorId(id);

        usuarioDao.deletar(usuario);

        finish();
    }

    public Usuario criaObjetoUsuario() {
        Usuario usuario = new Usuario();

        usuario.setNome(((EditText) findViewById(R.id.campoNome)).getText().toString());
        usuario.setTelefone(((EditText) findViewById(R.id.campoTelefone)).getText().toString());
        usuario.setEmail(((EditText) findViewById(R.id.campoEmail)).getText().toString());
        usuario.setCpf(((EditText) findViewById(R.id.campoCpf)).getText().toString());

        return usuario;
    }
}
