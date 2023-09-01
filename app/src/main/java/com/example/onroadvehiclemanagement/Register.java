package com.example.onroadvehiclemanagement;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {
    EditText ed_username, ed_email, ed_password,ed_name,ed_phonenumber,ed_cpassword;
    String str_name, str_email, str_password,str_username,str_phonenumber,str_cpassword;
    String url = "https://renthousesrilanka.lk/vehicle%20management/register.php";
    Button upload;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }



        setContentView(R.layout.activity_register);
        ed_name = findViewById(R.id.edittext_name);
        ed_email = findViewById(R.id.edittext_email);
        ed_username = findViewById(R.id.edittext_username);
        ed_phonenumber = findViewById(R.id.edittext_phonenumber);
        ed_password = findViewById(R.id.edittext_password);
        ed_cpassword = findViewById(R.id.edittext_confirmpassword);
        upload=findViewById(R.id.button_register);
        login = (TextView) findViewById(R.id.textview_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Register.this, MainActivity.class);
                startActivity(registerIntent);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                clearApplicationData();
            }


        });
    }


    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("EEEEEERRRRRROOOOOOORRRR", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            int i = 0;
            while (i < children.length) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
                i++;
            }
        }

        assert dir != null;
        return dir.delete();
    }
    public void register() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");


        if (ed_username.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
        } else if (ed_email.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        } else if (ed_password.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog.show();
            str_name = ed_name.getText().toString().trim();
            str_username = ed_username.getText().toString().trim();
            str_email = ed_email.getText().toString().trim();
            str_phonenumber = ed_phonenumber.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();
            str_cpassword = ed_cpassword.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    ed_name.setText("");
                    ed_username.setText("");
                    ed_email.setText("");
                    ed_phonenumber.setText("");
                    ed_password.setText("");
                    ed_cpassword.setText("");
                    Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("name", str_name);
                    params.put("username", str_username);
                    params.put("email", str_email);
                    params.put("phonenumber", str_phonenumber);
                    params.put("password", str_password);
                    params.put("cpassword", str_cpassword);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
            requestQueue.add(request);


        }

    }
}