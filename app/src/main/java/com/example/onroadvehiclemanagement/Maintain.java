package com.example.onroadvehiclemanagement;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Maintain extends AppCompatActivity {
    ImageView img;
    EditText ed_name, ed_owner_name, vehicle_number , date, description;
    String str_name, str_owner_name, str_vehicle_number,str_date,str_description;
    Button maintain, view, upload;
    Bitmap bitmap;
    String encodeImageString;
    String url = "https://renthousesrilanka.lk/vehicle%20management/maintain.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain);

        img = findViewById(R.id.image1);
        ed_name = findViewById(R.id.edittext1);
        ed_owner_name = findViewById(R.id.edittext2);
        vehicle_number = findViewById(R.id.edittext3);
        date = findViewById(R.id.edittext4);
        description = findViewById(R.id.edittext6);
        maintain = findViewById(R.id.btn1);
        view = findViewById(R.id.btn2);
        upload = findViewById(R.id.btn3);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Maintain.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                // update the text of the input field with the selected date
                                date.setText(day + "/" + (month+1) + "/" + year);
                            }
                        },
                        year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(Maintain.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }

        });
        maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maintain();
                clearApplicationData();
            }


        });


    }
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
{
    if(requestCode==1 && resultCode==RESULT_OK)
    {
        Uri filepath=data.getData();
        try
        {
            InputStream inputStream=getContentResolver().openInputStream(filepath);
            bitmap= BitmapFactory.decodeStream(inputStream);
            img.setImageBitmap(bitmap);
            encodeBitmapImage(bitmap);
        }catch (Exception ex)
        {

        }
    }
    super.onActivityResult(requestCode, resultCode, data);
}

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        int options = 90;
        while (byteArrayOutputStream.toByteArray().length / 1024 > 100) {  //Loop if compressed picture is greater than 400kb, than to compression
            byteArrayOutputStream.reset();//Reset baos is empty baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);//The compression options%, storing the compressed data to the baos
            options -= 10;//Every time reduced by 10
        }
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString= Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }
    public void view(View view) {
        Intent maintainIntent = new Intent(Maintain.this, YourMaintain.class);
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
    public void maintain() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");


        if (ed_name.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
        } else if (ed_owner_name.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        } else if (vehicle_number.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog.show();
            str_name = ed_name.getText().toString().trim();
            str_owner_name = ed_owner_name.getText().toString().trim();
            str_vehicle_number = vehicle_number.getText().toString().trim();
            str_date = date.getText().toString().trim();
            str_description = description.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    ed_name.setText("");
                    ed_owner_name.setText("");
                    vehicle_number.setText("");
                    date.setText("");
                    description.setText("");

                    img.setImageResource(R.drawable.ic_launcher_foreground);
                    Toast.makeText(Maintain.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Maintain.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("name", str_name);
                    params.put("ownername", str_owner_name);
                    params.put("vehiclenumber", str_vehicle_number);
                    params.put("date", str_date);
                    params.put("description", str_description);
                    params.put("upload",encodeImageString);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Maintain.this);
            requestQueue.add(request);


        }

    }
}
