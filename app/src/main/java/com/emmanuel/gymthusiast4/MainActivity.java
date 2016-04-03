package com.emmanuel.gymthusiast4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etUsername, etAge;
    Button logOutButton;
    UserLocalStore userLocalStore; //This gives the main activity class access to the user local store

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText)findViewById(R.id.etName);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etAge = (EditText)findViewById(R.id.etAge);
        logOutButton = (Button)findViewById(R.id.logOutButton);

        logOutButton.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this); // giving the new user local store a context that can be "got"
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (authenticate()==true){
            displayUserDetails();
        }else{
            startActivity(new Intent(MainActivity.this, Login.class)); //If the user is not logged in it starts the logged in activity screen
        }
    }

    //Below is a an authentication method which determines if a user is logged in. returns boolean true if logged in. else returns false.
    private boolean authenticate() {
        return userLocalStore.getUserLoggedIn();
    }

    //below displays user details

    private void displayUserDetails(){

        User user = userLocalStore.getLoggedInUser(); // here we get the details of the user who has logged in
        etUsername.setText(user.Username); // The next three lines of code set the infor gathered above into the edit text fields.
        etName.setText(user.Name);
        etAge.setText(user.Age + "");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logOutButton:

                userLocalStore.clearUserData(); // clears the user data upon logging out
                userLocalStore.setUserLoggedIn(false); // sets the boolean flag to false since the user has logged out

            startActivity(new Intent(this, Login.class));

            break;
        }
    }
}
