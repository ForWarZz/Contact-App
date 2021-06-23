package fr.forwarzz.tpone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import fr.forwarzz.tpone.R;
import fr.forwarzz.tpone.contacts.Contact;
import fr.forwarzz.tpone.utils.IContactListener;

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

        // Set action bar title and show back arrow

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Ajouter un contact");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Init all variables

        this.inputFirstName = findViewById(R.id.inputFirstName);
        this.inputLastName = findViewById(R.id.inputLastName);
        this.inputEmail = findViewById(R.id.inputEmail);
        this.inputPhone = findViewById(R.id.inputPhone);
        this.inputCity = findViewById(R.id.inputCity);
        this.favoriteSwitch = findViewById(R.id.favorite);

        this.confirmationButton = findViewById(R.id.addButton);

        // When the confirmation button was clicked, add new contact in the list if all field isn't empty

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

            contactListener.onContactAdded(new Contact(
                    inputFirstName.getText().toString(),
                    inputLastName.getText().toString(),
                    favoriteSwitch.isChecked(),
                    inputEmail.getText().toString(),
                    inputPhone.getText().toString(),
                    inputCity.getText().toString()
            ));

            finish();
        });
    }

    public static void setContactListener(IContactListener listener) {
        contactListener = listener;
    }
}