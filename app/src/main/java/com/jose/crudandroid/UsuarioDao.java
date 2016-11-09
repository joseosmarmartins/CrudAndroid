package com.jose.crudandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private CriaBanco banco;

    public UsuarioDao(Context context) {
        banco = new CriaBanco(context);
    }

    public Usuario usuarioPorId(Long id) {
        try {
            SQLiteDatabase db = banco.getReadableDatabase();
            Cursor cursor = db.query("USER", new String[]{"ID", "NOME", "CPF", "TELEFONE", "EMAIL"},
                    "ID = ?", new String[]{id.toString()}, null, null, null);

            Usuario usuario = null;

            while (cursor.moveToNext()) {
                usuario = new Usuario(cursor.getLong(cursor.getColumnIndex("ID")), cursor.getString(cursor.getColumnIndex("NOME")),
                        cursor.getString(cursor.getColumnIndex("TELEFONE")), cursor.getString(cursor.getColumnIndex("EMAIL")),
                        cursor.getString(cursor.getColumnIndex("CPF")));
            }

            db.close();

            return usuario;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Usuario> consultar(String nome) {
        try {
            SQLiteDatabase db = banco.getReadableDatabase();
            Cursor cursor = db.query("USER", new String[]{"ID", "NOME", "CPF", "TELEFONE", "EMAIL"},
                    nome != null && !nome.isEmpty() ? "NOME=?" : null,
                    nome != null && !nome.isEmpty() ? new String[]{nome} : null, null, null, null);

            List<Usuario> usuarioList = new ArrayList<>();

            while (cursor.moveToNext()) {
                Usuario usuario = new Usuario(cursor.getLong(cursor.getColumnIndex("ID")), cursor.getString(cursor.getColumnIndex("NOME")),
                        cursor.getString(cursor.getColumnIndex("TELEFONE")), cursor.getString(cursor.getColumnIndex("EMAIL")),
                        cursor.getString(cursor.getColumnIndex("CPF")));

                usuarioList.add(usuario);
            }

            db.close();

            return usuarioList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public int cadastrar(Usuario usuario) {
        try{
            SQLiteDatabase db = banco.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("NOME", usuario.getNome());
            valores.put("TELEFONE", usuario.getTelefone());
            valores.put("EMAIL", usuario.getEmail());
            valores.put("CPF", usuario.getCpf());

            db.insert("USER", null, valores);
            db.close();

            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public int editar(Usuario usuario) {
        try{
            SQLiteDatabase db = banco.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("NOME", usuario.getNome());
            valores.put("TELEFONE", usuario.getTelefone());
            valores.put("EMAIL", usuario.getEmail());
            valores.put("CPF", usuario.getCpf());

            db.update("USER", valores, "id=?", new String[]{usuario.getId().toString()});
            db.close();

            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public int deletar(Usuario usuario) {
        try{
            SQLiteDatabase db = banco.getWritableDatabase();

            db.delete("USER", "id=?", new String[]{usuario.getId().toString()});
            db.close();

            return 1;
        }catch (Exception e){
            return 0;
        }
    }
}
