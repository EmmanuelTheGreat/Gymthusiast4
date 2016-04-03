package com.emmanuel.gymthusiast4;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by user on 25/03/2016.
 */
public class Login extends AppCompatActivity implements View.OnClickListener {

    Button buttonLogin;
    EditText etUsername, etPassword;
    TextView registrationLink;
    UserLocalStore userLocalStore; // gives the login class access to the user local store

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /*find view by Id will look through the content view of the "login" activity xml ( referenced above)
        *and find the view with an Id called "loginButton", and cast this to a Button and then assign it to the variable
        *then assign this to the declared variable "buttonLogin".

         */
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        buttonLogin= (Button)findViewById(R.id.loginButton);
        registrationLink = (TextView) findViewById(R.id.registrationLink);


        //Below we set and "onClickListener" to listen for clicks and tell the button what to do when clicked.

        buttonLogin.setOnClickListener(this);
        registrationLink.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this); // giving a context to the new user local store
    }


    @Override
    public void onClick(View v) {
        /*switch statement allows us to know which button click notified the onClick method- clearer than if statements.
        *The switch gets the id of the view which notified this onClick method
        * Case statement tells the method what to do for each id that called it.
        * E.g. the loginButton id will have instructions when clicked but there could be multiple instructions for
        * different Id's etc.
         */
        switch(v.getId()){
            case R.id.loginButton:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User(username,password);

                authenticate(user);


                break;

            case R.id.registrationLink:
                startActivity(new Intent(this, Register.class));

                break;
        }
    }

    private void authenticate(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if(returnedUser ==null){
                    showErrorMessage();
                }else{
                    logUserIn(returnedUser);
                }
            }
        });

    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("User details are incorrect. Please re-enter");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();

    }

    private void logUserIn(User returnedUser){
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true); // sets the log in value to true if a user is indeed logged in.

        startActivity(new Intent(this, MainActivity.class));
    }
}
