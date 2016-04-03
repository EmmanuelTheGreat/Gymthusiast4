package com.emmanuel.gymthusiast4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 */
public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etUsername, etAge, etPassword;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        etName = (EditText)findViewById(R.id.etName);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etAge = (EditText)findViewById(R.id.etAge);
        etPassword = (EditText)findViewById(R.id.etPassword);
        registerButton = (Button)findViewById(R.id.registerButton);

        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.registerButton:

                String name = etName.getText().toString(); //This gets the text from the edit text view and converts to string
                int age = Integer.parseInt(etAge.getText().toString()); //Integer.parseInt allows us to get whatever info a user has entered into an integer format
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User (name, age, username, password); //Once a user registers we have their details as a new user is created.

                registerUser(user);
                break;
        }

    }

    private void registerUser(User user){
    ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(Register.this,Login.class ));
            }
        });

    }
}
