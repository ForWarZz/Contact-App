package bricout.maxence.tpone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bricout.maxence.tpone.contacts.Contact;

public class ContactInfoActivity extends AppCompatActivity {
    private TextView fullName;
    private TextView email;
    private TextView phone;
    private TextView city;

    private ImageView sendEmailButton;
    private ImageView sendSmsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        this.fullName = findViewById(R.id.fullNameInfo);
        this.email = findViewById(R.id.emailInfo);
        this.phone = findViewById(R.id.phoneInfo);
        this.city = findViewById(R.id.cityInfo);

        this.sendEmailButton = findViewById(R.id.sendEmailButton);
        this.sendSmsButton = findViewById(R.id.sendSmsButton);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Information sur le contact");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setContactValue((Contact) getIntent().getSerializableExtra(MainActivity.CONTACT));

        sendEmailButton.setOnClickListener(view -> {
            if (isEmailValid(email.getText().toString())) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);

                intent.setData(Uri.parse("mailto:" + email.getText().toString()));

                startActivity(intent);
            } else {
                Toast.makeText(this, "Desolé, l'adresse email enregistrée pour se contact est invalide.", Toast.LENGTH_LONG).show();
            }
        });

        sendSmsButton.setOnClickListener(view -> {
            if (isValidMobile(phone.getText().toString())) {
                Uri uri = Uri.parse("smsto:" + phone.getText().toString());

                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

                startActivity(intent);
            } else {
                Toast.makeText(this, "Désolé, le numéro enregistré pour se contact est invalide.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setContactValue(Contact contact) {
        fullName.setText(contact.getFullName());
        email.setText(contact.getEmail());
        phone.setText(contact.getMobile());
        city.setText(contact.getCity());
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
}