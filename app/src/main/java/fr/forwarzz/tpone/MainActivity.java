package fr.forwarzz.tpone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fr.forwarzz.tpone.R;
import fr.forwarzz.tpone.contacts.Contact;
import fr.forwarzz.tpone.contacts.ContactsAdapter;
import fr.forwarzz.tpone.listeners.ContactListener;

public class MainActivity extends AppCompatActivity {
    private static final String CONTACTS = "contacts";

    public static final String CONTACT = "contact";
    public static final String CONTACT_POSITION = "position";

    private SharedPreferences sharedPreferences;

    private RecyclerView recyclerView;
    private List<Contact> contacts;
    private ContactsAdapter contactsAdapter;

    private FloatingActionButton fab;

    private Gson gson;

    private ContactListener contactListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init all variables

        this.sharedPreferences = getSharedPreferences(HomeActivity.SHARED_PREFS, MODE_PRIVATE);

        Toast.makeText(this, "Bienvenue " + sharedPreferences.getString(HomeActivity.NAME, ""), Toast.LENGTH_SHORT).show();

        this.gson = new Gson();

        this.contactListener = new ContactListener(this);

        this.recyclerView = findViewById(R.id.contactList);
        this.fab = findViewById(R.id.addContactFAB);

        // Init list, loading from local data, add default elements if is null and sort list

        loadContacts();
        addDefaultElements();
        sortList();

        // Init the recycler view

        this.contactsAdapter = new ContactsAdapter(contacts, contactListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactsAdapter);

        // FAB -> Show new activity to add new contact in the list

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateContactActivity.class);

            CreateContactActivity.setContactListener(contactListener);

            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveContacts();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveContacts();
    }

    public void sortList() {
        contacts.sort(Comparator.comparing(Contact::isFavorite)
                .thenComparing(Contact::getLastName).reversed());
    }

    public void saveContacts() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(CONTACTS, gson.toJson(contacts));
        editor.apply();
    }

    public void loadContacts() {
        Type type = new TypeToken<ArrayList<Contact>>() {}.getType();

        this.contacts = gson.fromJson(sharedPreferences.getString(CONTACTS, null), type);
    }

    private void addDefaultElements() {
        if (contacts == null) {
            this.contacts = new ArrayList<>();

            contacts.add(new Contact("Arthur", "Dufour", false, "arthurdufour@gmail.com", "Pas de numéro", "Paris"));
            contacts.add(new Contact("Chistiane", "Roux", false, "christiane.roux@gmail.com", "Pas de numéro", "Genève"));
            contacts.add(new Contact("Charlie", "La Chocolaterie", true, "charlie.chocolaterie@gmail.com", "Pas de numéro", "Lausanne"));
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public ContactsAdapter getContactsAdapter() {
        return contactsAdapter;
    }
}