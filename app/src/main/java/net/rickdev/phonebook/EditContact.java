package net.rickdev.phonebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import net.rickdev.phonebook.clases.SQLiteConnection;

public class EditContact extends AppCompatActivity {

    SQLiteConnection objConnection;
    final String DATABASE_NAME = "contactsBook";

    EditText edtEditName, edtEditNumber;
    Button btnEditContact, btnDeleteContact, btnBack2, btnCall;

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
        btnCall = findViewById(R.id.btnCall);

        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContact();
            }
        });

        btnDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating string that will contain the message to display
                String title = "Delete Contact";
                String message = "Are you sure you want to delete this contact?";
                //setting the dialog builder to initiate the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(EditContact.this);
                //setting the title and message to display, this can also be done as an implicit string
                builder.setTitle(title);
                builder.setMessage(message);
                //setting the positive and negative buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //user confirms deletion of contact
                        deleteContact();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //user cancels deletion of contact
                                dialog.dismiss();
                            }
                        });
                //creating and showing the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactNumber = edtEditNumber.getText().toString();
            Intent callContact = new Intent(Intent.ACTION_DIAL);
            callContact.setData(Uri.parse("tel:" + contactNumber));
                startActivity(callContact);
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
        try {
            SQLiteDatabase myDataBase = objConnection.getWritableDatabase();
            String update = "UPDATE contacts SET name='"+edtEditName.getText()+"', phoneNumber='"+edtEditNumber.getText()+"'" +
                    " WHERE id_contact='"+idContact+"'";
            myDataBase.execSQL(update);
            myDataBase.close();
            Toast.makeText(EditContact.this, "Contact updated successfully", Toast.LENGTH_LONG).show();
            goBack();
        } catch (Exception error){
            Toast.makeText(EditContact.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void deleteContact(){
        try {
            SQLiteDatabase myDataBase = objConnection.getWritableDatabase();
            String delete = "DELETE FROM contacts WHERE id_contact='"+idContact+"'";
            myDataBase.execSQL(delete);
            myDataBase.close();
            Toast.makeText(EditContact.this, "Contact deleted successfully", Toast.LENGTH_LONG).show();
            goBack();
        } catch (Exception error){
            Toast.makeText(EditContact.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void goBack(){
        Intent goBack = new Intent(EditContact.this, MainActivity.class);
        startActivity(goBack);
        EditContact.this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle extraValues = getIntent().getExtras();
        if (extraValues == null){
            Toast.makeText(EditContact.this, "Missing contact ID", Toast.LENGTH_SHORT).show();
            idContact = 0;
            goBack();
        } else {
            idContact = extraValues.getInt("id_contact");
            seeContact();
        }
    }

    private void seeContact(){
        SQLiteDatabase base = objConnection.getReadableDatabase();
        String query = "SELECT id_contact, name, phoneNumber FROM contacts WHERE id_contact = '"+idContact+"'";
        Cursor eachRegistry = base.rawQuery(query, null);
        if (eachRegistry.moveToFirst()){
            do {
            edtEditName.setText(eachRegistry.getString(1));
            edtEditNumber.setText(eachRegistry.getString(2));
            }while(eachRegistry.moveToNext());
        }
    }
}