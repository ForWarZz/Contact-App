package bricout.maxence.tpone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bricout.maxence.tpone.contacts.Contact;
import bricout.maxence.tpone.contacts.ContactsAdapter;
import bricout.maxence.tpone.utils.IContactClickListener;
import bricout.maxence.tpone.utils.IContactListener;

public class MainActivity extends AppCompatActivity implements IContactListener, IContactClickListener {
    private static final String CONTACTS = "contacts";

    public static final String CONTACT = "contact";
    public static final String CONTACT_POSITION = "position";

    private RecyclerView recyclerView;
    private List<Contact> contacts;
    private ContactsAdapter contactsAdapter;

    private FloatingActionButton fab;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(HomeActivity.SHARED_PREFS, MODE_PRIVATE);

        Toast.makeText(this, "Bienvenue " + sharedPreferences.getString(HomeActivity.NAME, ""), Toast.LENGTH_SHORT).show();

        this.gson = new Gson();

        this.recyclerView = findViewById(R.id.contactList);
        this.fab = findViewById(R.id.addContactFAB);

        loadContacts();

        if (contacts.isEmpty()) {
            contacts.add(new Contact("Arthur", "Dufour", false, "arthurdufour@gmail.com", "Pas de numéro", "Paris"));
            contacts.add(new Contact("Chistiane", "Roux", false, "christiane.roux@gmail.com", "Pas de numéro", "Genève"));
            contacts.add(new Contact("Charlie", "La Chocolaterie", true, "charlie.chocolaterie@gmail.com", "Pas de numéro", "Lausanne"));
        }

        sortList();

        this.contactsAdapter = new ContactsAdapter(contacts, this, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactsAdapter);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateContactActivity.class);

            CreateContactActivity.setContactListener(this);

            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveContacts();
    }

    @Override
    public void onContactAdded(Contact contact) {
        contacts.add(contact);

        saveContacts();
        sortList();

        contactsAdapter.notifyItemInserted(contacts.indexOf(contact));
    }

    @Override
    public void onContactRemoved(int position) {
        contacts.remove(position);

        sortList();
        saveContacts();

        contactsAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onContactUpdated() {
        sortList();
        saveContacts();

        recyclerView.post(() -> contactsAdapter.notifyDataSetChanged());
    }

    @Override
    public void onContactClick(int position) {
        Intent intent = new Intent(this, ContactInfoActivity.class);
        ContactInfoActivity.setContactListener(this);

        intent.putExtra(CONTACT, contacts.get(position));
        intent.putExtra(CONTACT_POSITION, position);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveContacts();
    }

    private void sortList() {
        contacts.sort(Comparator.comparing(Contact::isFavorite)
                .thenComparing(Contact::getLastName).reversed());
    }

    private void saveContacts() {
        SharedPreferences sharedPreferences = getSharedPreferences(HomeActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(CONTACTS, gson.toJson(contacts));
        editor.apply();
    }

    private void loadContacts() {
        SharedPreferences sharedPreferences = getSharedPreferences(HomeActivity.SHARED_PREFS, MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<Contact>>() {}.getType();

        this.contacts = gson.fromJson(sharedPreferences.getString(CONTACTS, null), type);

        if (contacts == null) this.contacts = new ArrayList<>();
    }
}