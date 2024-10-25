package net.rickdev.phonebook;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import net.rickdev.phonebook.clases.SQLiteConnection;

public class AddContact extends AppCompatActivity {

    SQLiteConnection objConnection;
    final String DATABASE_NAME = "contactsBook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);

        objConnection = new SQLiteConnection(AddContact.this, DATABASE_NAME, null, 1);
    }
}