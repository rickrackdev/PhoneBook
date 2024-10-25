package net.rickdev.phonebook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import net.rickdev.phonebook.clases.SQLiteConnection;

public class AddContact extends AppCompatActivity {

    SQLiteConnection objConnection;
    final String DATABASE_NAME = "contactsBook";

    EditText edtName, edtNumber;
    Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);

        objConnection = new SQLiteConnection(AddContact.this, DATABASE_NAME, null, 1);
         edtName = findViewById(R.id.edtName);
         edtNumber = findViewById(R.id.edtNumber);
         btnSave = findViewById(R.id.btnSave);
         btnBack = findViewById(R.id.btnBack);

         btnSave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                addContact();
             }
         });

         btnBack.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 goBack();
             }
         });
    }


    private void addContact(){
        try {
            SQLiteDatabase myDataBase = objConnection.getWritableDatabase();
            String insert = "INSERT INTO contacts (name, phoneNumber) VALUES ('"+edtName.getText()+"','"+edtNumber.getText()+"')";
            myDataBase.execSQL(insert);
            myDataBase.close();
            Toast.makeText(AddContact.this, "Contact saved succesfully", Toast.LENGTH_LONG).show();
        } catch (Exception error) {
            Toast.makeText(AddContact.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void goBack(){
        Intent goBack = new Intent(AddContact.this, MainActivity.class);
        startActivity(goBack);
        AddContact.this.finish();
    }
}