package com.example.cmpp264lab4;

import static com.example.cmpp264lab4.Validator.validateNonEmptyPositiveInteger;
import static com.example.cmpp264lab4.Validator.validateTextNonEmpty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// detail activity to add/edit agents

public class DetailActivity extends AppCompatActivity {
    EditText etFirstName;
    EditText etMiddleInitial;
    EditText etLastName;
    EditText etPhone;
    EditText etEmail;
    EditText etPosition;
    EditText etAgencyId;

    Button btnSave;
    Button btnDelete;

    Agent agent = null;
    String mode = "";
    AgentDB dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etFirstName = findViewById(R.id.etFirstName);
        etMiddleInitial = findViewById(R.id.etMiddleInitial);
        etLastName = findViewById(R.id.etLastName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPosition = findViewById(R.id.etPosition);
        etAgencyId = findViewById(R.id.etAgencyId);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        dataSource = new AgentDB(this);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");

        // if mode is Edit, populate fields with existing agent info
        if(mode.equals("Edit")){
            agent = (Agent) intent.getSerializableExtra("agent");
            etFirstName.setText(agent.getAgtFirstName());
            etMiddleInitial.setText(agent.getAgtMiddleInitial());
            etLastName.setText(agent.getAgtLastName());
            etPhone.setText(agent.getAgtBusPhone());
            etEmail.setText(agent.getAgtEmail());
            etPosition.setText(agent.getAgtPosition());
            etAgencyId.setText(Integer.toString(agent.getAgencyId()));
            btnDelete.setEnabled(true);
        } else { // Add mode
            btnDelete.setEnabled(false);
        }
        btnSave.setOnClickListener(v -> {
            // validate first, and when validated, set agent values
            if (validateAgentInputs()) {
                String firstName = etFirstName.getText().toString();
                String middleInitial = etMiddleInitial.getText().toString();
                String lastName = etLastName.getText().toString();
                String busPhone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String position = etPosition.getText().toString();
                int agencyId = Integer.parseInt(etAgencyId.getText().toString());
                if (mode.equals("Edit")) {
                    agent.setAgtFirstName(firstName);
                    agent.setAgtMiddleInitial(middleInitial);
                    agent.setAgtLastName(lastName);
                    agent.setAgtBusPhone(busPhone);
                    agent.setAgtEmail(email);
                    agent.setAgtPosition(position);
                    agent.setAgencyId(agencyId);
                    dataSource.updateAgent(agent);
                    finish();
                } else { // Add
                    agent = new Agent(
                            0,
                            firstName,
                            middleInitial,
                            lastName,
                            busPhone,
                            email,
                            position,
                            agencyId
                    );
                    dataSource.insertAgent(agent);
                    finish();
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
                dataSource.deleteAgent(agent.getAgentId());
                finish();
            });
    } // end onCreate

    // validate method that uses Validator custom methods and Patterns methods
    private boolean validateAgentInputs() {
        String CANNOT_BE_EMPTY = " cannot be empty";
        boolean noError = true;
        if(!validateTextNonEmpty(etFirstName)){
            etFirstName.setError("First Name"+CANNOT_BE_EMPTY);
            noError = false;
        }
        if(!validateTextNonEmpty(etLastName)){
            etLastName.setError("Last Name"+CANNOT_BE_EMPTY);
            noError = false;
        }
        if(!validateTextNonEmpty(etPhone)){
            etPhone.setError("Phone Number"+CANNOT_BE_EMPTY);
            noError = false;
        } else if (!Patterns.PHONE.matcher(etPhone.getText()).matches()){
            etPhone.setError("Please enter a valid phone number. ex. ###-###-####");
        }

        if(!validateTextNonEmpty(etEmail)){
            etEmail.setError("Email"+CANNOT_BE_EMPTY);
            noError = false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()){
            etEmail.setError("Please enter a valid email.");
            noError = false;
        }

        if(!validateTextNonEmpty(etPosition)){
            etPosition.setError("Position"+CANNOT_BE_EMPTY);
            noError = false;
        }
        if(!validateNonEmptyPositiveInteger(etAgencyId)){
            etAgencyId.setError("Agency Id must be a positive integer.");
            noError = false;
        }
        return noError;
    }
}