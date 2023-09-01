package com.example.onroadvehiclemanagement;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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

public class Owner extends AppCompatActivity {

    EditText mileage, fuel, contact , date, situation;
    String str_mileage, str_fuel, str_contact,str_date,str_situation;
    Button submit, view;
    String url = "https://renthousesrilanka.lk/vehicle%20management/owner.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

     //   img = findViewById(R.id.image1);
        mileage = findViewById(R.id.edittext1);
        fuel = findViewById(R.id.edittext2);
        contact = findViewById(R.id.edittext3);
        date = findViewById(R.id.edittext4);
        situation = findViewById(R.id.edittext6);
        submit = findViewById(R.id.btn1);
        view = findViewById(R.id.btn2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
                clearApplicationData();
            }


        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent(Owner.this, com.example.onroadvehiclemanagement.Home.class);
                startActivity(viewIntent);
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
    public void submit() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        if (mileage.getText().toString().equals("")) {
            Toast.makeText(this, "Enter mileage", Toast.LENGTH_SHORT).show();
        } else if (fuel.getText().toString().equals("")) {
            Toast.makeText(this, "Enter fuel", Toast.LENGTH_SHORT).show();
        } else if (contact.getText().toString().equals("")) {
            Toast.makeText(this, "Enter contact", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog.show();
            str_mileage = mileage.getText().toString().trim();
            str_fuel = fuel.getText().toString().trim();
            str_contact = contact.getText().toString().trim();
            str_date = date.getText().toString().trim();
            str_situation = situation.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    mileage.setText("");
                    fuel.setText("");
                    contact.setText("");
                    date.setText("");
                    situation.setText("");

                    Toast.makeText(Owner.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Owner.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("mileage", str_mileage);
                    params.put("fuel", str_fuel);
                    params.put("contact", str_contact);
                    params.put("date", str_date);
                    params.put("situation", str_situation);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Owner.this);
            requestQueue.add(request);


        }
    }
}