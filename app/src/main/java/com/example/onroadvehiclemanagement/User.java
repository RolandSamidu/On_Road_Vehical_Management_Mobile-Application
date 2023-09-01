package com.example.onroadvehiclemanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class User extends AppCompatActivity {
    EditText mTextId;
    EditText mTextName;
    EditText mTextUsername;
    EditText mTextEmail;
    EditText mTextPhoneNumber;
    Button mButtonUpdate;

    int code;
    int position = 0;
    boolean alreadyRegister;
    String result = null;
    String id,name,username,email,phonenumber;

    String intentusername;

    private static final String url="https://renthousesrilanka.lk/vehicle%20management/updateUser.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mTextId = (EditText) findViewById(R.id.edittext_id);
        intentusername = getIntent().getStringExtra("EXTRA_USERNAME");
        mTextName = (EditText) findViewById(R.id.edittext_name);
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextEmail = (EditText) findViewById(R.id.edittext_email);
        mTextPhoneNumber = (EditText) findViewById(R.id.edittext_phonenumber);
        //mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mButtonUpdate = (Button) findViewById(R.id.button_register);

        mTextEmail.setText(intentusername);



        if(intentusername.isEmpty()){
            Toast.makeText(User.this,"Username is Empty", Toast.LENGTH_LONG).show();
        }else{
            SelectUserDetails();
        }
        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected() == true){
                    clearApplicationData();
                    UpdateUserDetails();
                    Intent intent1 = new Intent(User.this,Home.class);
                    intent1.putExtra("EXTRA_USERNAME", email);
                    //Toast.makeText(EditProfile.this,"Update succefully!", Toast.LENGTH_LONG).show();
                    startActivity(intent1);
                }else{
                    Toast.makeText(User.this,"Check your Internet Connection!", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    //read data from online database
    public void SelectUserDetails(){

        class SendPostReqAsyncTask extends AsyncTask<String,Void,String> {

            String id, name, username,email,phonenumber;
            String ppicture;

            @Override
            protected String doInBackground(String... strings) {

                try {
                    URL url = new URL("https://renthousesrilanka.lk/vehicle%20management/selectUser.php?email="+ intentusername);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));

                    String line = null;
                    StringBuilder sb = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                    Log.e("pass 2", "connection success ");
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                }

                try {
                    JSONObject json_data = new JSONObject(result);
                    id = (json_data.getString("id"));
                    name = (json_data.getString("name"));
                    username = (json_data.getString("username"));
                    email = (json_data.getString("email"));
                    phonenumber = (json_data.getString("phonenumber"));


//------------------------------------


                    //---------------------------------------------------

                } catch (Exception e) {
                    Log.e("Fail 3", e.toString());

                }
                return "Data Select Successfully";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null){
                    // Do you work here on success
                    mTextId.setText(id);
                    mTextName.setText(name);
                    mTextUsername.setText(username);
                    mTextEmail.setText(email);
                    mTextPhoneNumber.setText(phonenumber);



                }else{
                    // null response or Exception occur

                }

                // mTextPassword.setText(password);
                //  byte[] image = res.getBlob(6);

                // -----------------------------------------------------------------


                //-----------------------------------------------------------------
//                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
//                mimageView.setImageBitmap(bmp);


            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    public boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            // Toast.makeText(SignUp.this,"Internet Successful!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            // Toast.makeText(SignUp.this,"Internet Fail!", Toast.LENGTH_LONG).show();
            return false;

        }
    }

//    .=// @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
//    {
//        if(requestCode==1 && resultCode==RESULT_OK)
//        {
//            Uri filepath=data.getData();
//            try
//            {
//                InputStream inputStream=getContentResolver().openInputStream(filepath);
//                bitmap= BitmapFactory.decodeStream(inputStream);
//                mimageView.setImageBitmap(bitmap);
//                encodeBitmapImage(bitmap);
//            }catch (Exception ex)
//            {
//
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void encodeBitmapImage(Bitmap bitmap)
//    {
//        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//        int options = 90;
//        while (byteArrayOutputStream.toByteArray().length / 1024 > 100) {  //Loop if compressed picture is greater than 400kb, than to compression
//            byteArrayOutputStream.reset();//Reset baos is empty baos
//            bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);//The compression options%, storing the compressed data to the baos
//            options -= 10;//Every time reduced by 10
//        }
//        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
//        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
//    }
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
//    //update online database
    public void UpdateUserDetails(){

//        id = mTextId.getText().toString().trim();
//        name =   mTextName.getText().toString().trim();
//        username =   mTextUsername.getText().toString().trim();
//        email =   mTextEmail.getText().toString().trim();
//        phonenumber =   mTextPhoneNumber.getText().toString().trim();
//
//
//        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{
//
//            @Override
//            protected String doInBackground(String... strings) {
//
//                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                nameValuePairs.add(new BasicNameValuePair("id",id));
//                nameValuePairs.add(new BasicNameValuePair("name",name));
//                nameValuePairs.add(new BasicNameValuePair("username",username));
//                nameValuePairs.add(new BasicNameValuePair("email",email));
//                nameValuePairs.add(new BasicNameValuePair("phonenumber",phonenumber));
//
//                try {
//                    HttpClient httpClient = new DefaultHttpClient();
//                    String ServerURL = "https://www.nawagamuwadevalaya.com/SalonUpdateUser.php";
//                    HttpPost httpPost = new HttpPost(ServerURL);
//                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                    HttpResponse httpResponse = httpClient.execute(httpPost);
//                    HttpEntity entity = httpResponse.getEntity();
//                }
//                catch(Exception e) {
//                    Log.e("Fail 1", e.toString());
//                    Toast.makeText(getApplicationContext(), "Invalid IP Address",
//                            Toast.LENGTH_LONG).show();
//                }
//
//                try {
//                    URL url = new URL("https://www.nawagamuwadevalaya.com/SalonUpdateUser.php");
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                 //   try {
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
//                        String line = null;
//                        StringBuilder sb = new StringBuilder();
//                     //  } finally {
//                    //    urlConnection.disconnect();
//                    //}
//
//
//                    while ((line = reader.readLine()) != null)
//                    {
//                        sb.append(line + "\n");
//                    }
//                    result = sb.toString();
//                    Log.e("pass 2", "connection success ");
//                }
//
//
//                catch(Exception e) {
//                    Log.e("Fail 2", e.toString());
//                }
//
//                try {
//                    JSONObject json_data = new JSONObject(result);
//                    code =(json_data.getInt("code"));
//                }
//                catch(Exception e) {
//                    Log.e("Fail 3", e.toString());
//
//                }
//
//
//                return "Data Update Successfully";
//
//
//            }
//
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//
//
//                if(code ==0)
//                {
//                    Toast.makeText(EditProfile.this, "Data Update fail!", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(EditProfile.this, "Data Update Successful!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
//
//        sendPostReqAsyncTask.execute();

        {
            id = mTextId.getText().toString().trim();
            name =   mTextName.getText().toString().trim();
            username =   mTextUsername.getText().toString().trim();
            email =   mTextEmail.getText().toString().trim();
            phonenumber =   mTextPhoneNumber.getText().toString().trim();

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Adding data....");
            progressDialog.show();

            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    mTextId.setText("");
                    mTextName.setText("");
                    mTextUsername.setText("");
                    mTextEmail.setText("");
                    mTextPhoneNumber.setText("");
//                   img.setImageResource(R.drawable.ic_launcher_foreground);
                    Toast.makeText(User.this, response, Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String,String> map=new HashMap<String, String>();
                    map.put("id",id);
                    map.put("name",name);
                    map.put("username",username);
                    map.put("email",email);
                    map.put("phonenumber",phonenumber);
                    return map;
                }
            };


            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }
    }


}