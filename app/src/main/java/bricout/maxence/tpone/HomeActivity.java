package bricout.maxence.tpone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "bricout.maxence.sharedPreferences";
    public static final String NAME = "name";

    private Button confirmationButton;
    private EditText inputName;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();

        if (name.isEmpty()) {
            setContentView(R.layout.activity_home);

            this.inputName = findViewById(R.id.inputName);
            this.confirmationButton = findViewById(R.id.confirmationButton);

            confirmationButton.setOnClickListener(view -> {
                if (inputName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Veillez à bien entrer votre prénom correctement.", Toast.LENGTH_SHORT).show();
                    return;
                }

                this.name = inputName.getText().toString();

                saveData();
                switchActivity();
            });
        } else {
            switchActivity();
        }
    }

    private void switchActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
        finish();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(NAME, inputName.getText().toString());
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        this.name = sharedPreferences.getString(NAME, "");
    }
}