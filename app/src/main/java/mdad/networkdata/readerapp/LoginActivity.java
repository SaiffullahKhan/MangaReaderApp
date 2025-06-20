package mdad.networkdata.readerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private static String url_login = MainActivity.ipAddress + "/login.php";

    Button loginBtn;
    Button registerBtn;
    EditText usernameEditText, passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // this is to initialise the buttons
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);




        // login button in LoginActivity to login by checking from db
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                Map<String, String> params_create = new HashMap<>();
                params_create.put("id", username);
                params_create.put("pw", password);

                postData(url_login, params_create);
            }
        });



        // for register button in LoginActivity to redirect to RegisterActivity
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


    public void postData(String url, Map<String, String> params) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Log.i("tss", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("tss", "===>" + response + "<===");
                if (response.equals("Error"))
                {
                    Toast.makeText(getApplicationContext(),"Error in updating database",
                            Toast.LENGTH_LONG).show();
                }
                else if(response.equals("Success"))
                {
                    Toast.makeText(getApplicationContext(),"Login Successful",
                            Toast.LENGTH_LONG).show();
                    finish();

                    // Goes to BooksActivity after successfull login
                    Intent i = new Intent (getApplicationContext(), BooksActivity.class);
                    startActivity(i);
                }

                else

                    Toast.makeText(getApplicationContext(),"Failed to Login / wrong user data",Toast.LENGTH_LONG).show();

            }
        },
                //error in Volley
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error
                        Toast.makeText(getApplicationContext(),"Error in accessing database",Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Nullable
            @Override
            // to send product info stored in HashMap params_create to server via HTTP Post
            protected Map<String, String> getParams() {
                return params;
            }
        };
        //add StringRequest to Volley Queue
        requestQueue.add(stringRequest);
    }
}