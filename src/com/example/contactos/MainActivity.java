package com.example.contactos;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText et1, et2, et3, et4, et5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);
        et5 = (EditText) findViewById(R.id.editText5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.activity_main, menu);
        return true;
    }

    public void guardar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        
        SQLiteDatabase bd = admin.getWritableDatabase();
        String celular= et1.getText().toString();
        String nombre = et2.getText().toString();
        String apellido = et3.getText().toString();
        String telefono = et4.getText().toString();
        String correo= et5.getText().toString();
        
        ContentValues registro = new ContentValues();
        
        registro.put("celular", celular);
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("telefono", telefono);
        registro.put("correo", correo);
        bd.insert("contactos", null, registro);
        
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");
        Toast.makeText(this, "Se cargaron los datos de la persona",
                Toast.LENGTH_SHORT).show();
    }

    public void consulta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String celular = et1.getText().toString();
        Cursor fila = bd.rawQuery(
                "select nombre,apellido,telefono,correo from contactos where celular=" + celular, null);
        if (fila.moveToFirst()) {
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));
            et4.setText(fila.getString(2));
            et5.setText(fila.getString(3));
        } else
            Toast.makeText(this, "No existe una persona con dicho numero",
                    Toast.LENGTH_SHORT).show();
        bd.close();

    }

    public void borrar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String celular = et1.getText().toString();
        int cant = bd.delete("contactos", "celular=" + celular, null);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");
        if (cant == 1)
            Toast.makeText(this, "Se borró la persona con dicho numero",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe ese numero de celular",
                    Toast.LENGTH_SHORT).show();
    }

    public void modificacion(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String celular= et1.getText().toString();
        String nombre = et2.getText().toString();
        String apellido = et3.getText().toString();
        String telefono = et4.getText().toString();
        String correo= et5.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("telefono", telefono);
       registro.put("correo", correo);
        int cant = bd.update("contactos", registro, "celular=" + celular, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe una persona con dicho numero",
                    Toast.LENGTH_SHORT).show();
    }

}