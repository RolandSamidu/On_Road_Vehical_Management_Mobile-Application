package com.example.onroadvehiclemanagement;

import static com.example.onroadvehiclemanagement.MainActivity.deleteDir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public class ForgetPassword extends AppCompatActivity {

    EditText email;
    Button btnSubmit;
    String emailAdd;
    Button return_icon;
    StringRequest stringRequest;
    String URL = "https://renthousesrilanka.lk/vehicle%20management/forgetPassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        btnSubmit = (Button) findViewById(R.id.submit);
        email = (EditText) findViewById(R.id.forget);
        return_icon = (Button) findViewById(R.id.return_icon);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
                clearApplicationData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.return_icon:
                Toast.makeText(this, "Return Page", Toast.LENGTH_SHORT).show();
                return_icon(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void return_icon(MenuItem item) {
        Intent maintainIntent = new Intent(ForgetPassword.this, com.example.onroadvehiclemanagement.MainActivity.class);
        startActivity(maintainIntent);
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

    private void submit() {
        if (email.getText().toString().equals("")){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait..");

            progressDialog.show();

            emailAdd = email.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    if (response.equalsIgnoreCase("SUCCESS")) {
                        Toast.makeText(ForgetPassword.this, "Email successfully sent please your mail box", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgetPassword.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgetPassword.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", emailAdd);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(ForgetPassword.this);
            requestQueue.add(request);
        }
    }
}

