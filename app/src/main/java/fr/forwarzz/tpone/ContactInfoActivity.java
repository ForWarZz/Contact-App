package fr.forwarzz.tpone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.forwarzz.tpone.contacts.Contact;
import fr.forwarzz.tpone.utils.IContactListener;

public class ContactInfoActivity extends AppCompatActivity {
    private TextView fullName;
    private TextView email;
    private TextView phone;
    private TextView city;

    private ImageView sendEmailButton;
    private ImageView sendSmsButton;

    private ImageButton removeContactButton;

    private Contact targetContact;
    private int targetPosition;

    private static IContactListener contactListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        // Init all variables

        this.fullName = findViewById(R.id.fullNameInfo);
        this.email = findViewById(R.id.emailInfo);
        this.phone = findViewById(R.id.phoneInfo);
        this.city = findViewById(R.id.cityInfo);

        this.sendEmailButton = findViewById(R.id.sendEmailButton);
        this.sendSmsButton = findViewById(R.id.sendSmsButton);

        this.removeContactButton = findViewById(R.id.contactRemoveIcon);

        this.targetContact = (Contact) getIntent().getSerializableExtra(MainActivity.CONTACT);
        this.targetPosition = getIntent().getExtras().getInt(MainActivity.CONTACT_POSITION);

        // Define custom toolbar

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Change action bar title and enable back arrow

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Informations");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set activity values from contact values

        setContactValue();

        // When email button is clicked, open intent to send email

        sendEmailButton.setOnClickListener(view -> {
            if (isEmailValid(email.getText().toString())) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);

                intent.setData(Uri.parse("mailto:" + email.getText().toString()));

                startActivity(intent);
            } else {
                Toast.makeText(this, "Désolé, l'adresse email enregistrée pour se contact est invalide.", Toast.LENGTH_LONG).show();
            }
        });

        // When sms button is clicked, open intent to send sms

        sendSmsButton.setOnClickListener(view -> {
            if (isValidMobile(phone.getText().toString())) {
                Uri uri = Uri.parse("smsto:" + phone.getText().toString());

                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

                startActivity(intent);
            } else {
                Toast.makeText(this, "Désolé, le numéro enregistré pour se contact est invalide.", Toast.LENGTH_LONG).show();
            }
        });

        // When remove button in action bar is clicked, remove the contact

        removeContactButton.setOnClickListener(view -> {
            finish();

            contactListener.onContactRemoved(targetPosition);
        });
    }

    private void setContactValue() {
        fullName.setText(targetContact.getFullName());
        email.setText(targetContact.getEmail());
        phone.setText(targetContact.getMobile());
        city.setText(targetContact.getCity());
    }

    private boolean isEmailValid(String email) {
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 13;
        }
        return false;
    }

    public static void setContactListener(IContactListener contactListener) {
        ContactInfoActivity.contactListener = contactListener;
    }
}