package bricout.maxence.tpone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import bricout.maxence.tpone.contacts.Contact;
import bricout.maxence.tpone.utils.IContactListener;

public class CreateContactActivity extends AppCompatActivity {
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputEmail;
    private EditText inputPhone;
    private EditText inputCity;
    private SwitchMaterial favoriteSwitch;

    private Button confirmationButton;

    private static IContactListener contactListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Ajouter un contact");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        this.inputFirstName = findViewById(R.id.inputFirstName);
        this.inputLastName = findViewById(R.id.inputLastName);
        this.inputEmail = findViewById(R.id.inputEmail);
        this.inputPhone = findViewById(R.id.inputPhone);
        this.inputCity = findViewById(R.id.inputCity);
        this.favoriteSwitch = findViewById(R.id.favorite);

        this.confirmationButton = findViewById(R.id.addButton);

        confirmationButton.setOnClickListener(view -> {
            if (inputFirstName.getText().toString().isEmpty() ||
                    inputLastName.getText().toString().isEmpty() ||
                    inputEmail.getText().toString().isEmpty() ||
                    inputPhone.getText().toString().isEmpty() ||
                    inputCity.getText().toString().isEmpty())

            {
                Toast.makeText(this, "Tous les champs sont obligatoire.", Toast.LENGTH_SHORT).show();
                return;
            }

            Contact newContact = new Contact(
                    inputFirstName.getText().toString(),
                    inputLastName.getText().toString(),
                    favoriteSwitch.isChecked(),
                    inputEmail.getText().toString(),
                    inputPhone.getText().toString(),
                    inputCity.getText().toString()
            );

            contactListener.onContactAdded(newContact);

            finish();
        });
    }

    public static void setContactListener(IContactListener listener) {
        contactListener = listener;
    }
}