package net.rickdev.phonebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import net.rickdev.phonebook.clases.SQLiteConnection;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLiteConnection objConnection;
    final String DATABASE_NAME = "contactsBook";

    Button btnSearch, btnAdd;
    ListView contactList;
    ArrayList<String> list;
    ArrayAdapter adapter;

    EditText edtSearch;

    List<Integer> searchID = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objConnection = new SQLiteConnection(MainActivity.this, DATABASE_NAME, null, 1);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addContact = new Intent(MainActivity.this, AddContact.class);
                startActivity(addContact);
            }
        });

        contactList = findViewById(R.id.lvContacts);
        edtSearch = findViewById(R.id.edtSearch);
        list = fillList();
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
        contactList.setAdapter(adapter);

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedID = searchID.get(position);
                Intent editContact = new Intent(MainActivity.this, EditContact.class);
                editContact.putExtra("id_contact", selectedID);
                startActivity(editContact);
            }
        });
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = fillList();
                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
                contactList.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            list.clear();
            list = fillList();
            adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
            contactList.setAdapter(adapter);
        }catch (Exception error){
            Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList fillList(){
        ArrayList<String> myList = new ArrayList<>();
        SQLiteDatabase base = objConnection.getReadableDatabase();
        String search = edtSearch.getText().toString();
        String query = "SELECT id_contact, name, phoneNumber FROM contacts WHERE name LIKE '%"+search+"%' OR phoneNumber LIKE '%"+search+"%' ORDER BY name ASC";
        Cursor eachRegistry = base.rawQuery(query, null);
        searchID.clear();
        if (eachRegistry.moveToFirst()){
            do{
                myList.add(eachRegistry.getString(1).toString() + " - " + eachRegistry.getString(2).toString());
                searchID.add(eachRegistry.getInt(0));
            }while (eachRegistry.moveToNext());
        }
        return myList;
    }

}