package net.rickdev.phonebook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import net.rickdev.phonebook.clases.SQLiteConnection;

public class EditContact extends AppCompatActivity {

    SQLiteConnection objConnection;
    final String DATABASE_NAME = "contactsBook";

    EditText edtEditName, edtEditNumber;
    Button btnEditContact, btnDeleteContact, btnBack2;

    int idContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        objConnection = new SQLiteConnection(EditContact.this, DATABASE_NAME, null, 1);
        edtEditName = findViewById(R.id.edtEditName);
        edtEditNumber = findViewById(R.id.edtEditNumber);
        btnEditContact = findViewById(R.id.btnEditContact);
        btnDeleteContact = findViewById(R.id.btnDeleteContact);
        btnBack2 = findViewById(R.id.btnBack2);

        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContact();
            }
        });

        btnDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact();
            }
        });

        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void editContact(){

    }

    private void deleteContact(){

    }

    private void goBack(){

    }
}