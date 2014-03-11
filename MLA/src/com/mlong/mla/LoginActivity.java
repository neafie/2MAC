package com.mlong.mla;

import library.DatabaseHandler;
import library.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class LoginActivity extends Activity {
	Button btnLogin;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
    
 // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // setting default screen to login.xml
        setContentView(R.layout.login);
        
        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);

        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        
     // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            @Override
			public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                //new MyAsyncTask().execute(email, password);
                // Launch Dashboard Screen
                Intent dashboard = new Intent(getApplicationContext(), VieTabActivity.class);
                 
                // Close all views before launching Dashboard
                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashboard);
                 
                // Close Login Screen
                finish();
            }
        });
 
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
 
            @Override
			public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<String, Void, JSONObject> {
        
        @Override
		protected JSONObject doInBackground(String... params) {
                UserFunctions userFunction = new UserFunctions();
                if (params.length != 2)
                        return null;
                JSONObject json = userFunction.loginUser(params[0], params[1]);
                return json;
        }
       
        @Override
		protected void onPostExecute(JSONObject json) {
                try {
            if (json != null && json.getString(KEY_SUCCESS) != null) {
                loginErrorMsg.setText("");
                String res = json.getString(KEY_SUCCESS);
                if(Integer.parseInt(res) == 1){
                    // user successfully logged in
                    // Store user details in SQLite Database
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    JSONObject json_user = json.getJSONObject("user");
                     
                    // Clear all previous data in database
                    UserFunctions userFunction = new UserFunctions();
                    userFunction.logoutUser(getApplicationContext());
                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
                     
                    // Launch Dashboard Screen
                    Intent dashboard = new Intent(getApplicationContext(), VieTabActivity.class);
                     
                    // Close all views before launching Dashboard
                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dashboard);
                     
                    // Close Login Screen
                    finish();
                }else{
                    // Error in login
                    loginErrorMsg.setText("Incorrect username/password");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
}
}
