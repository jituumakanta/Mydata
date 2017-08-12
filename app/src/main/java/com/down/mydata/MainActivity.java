package com.down.mydata;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<bean> allData;
    MyAdapter adapter;

    RecyclerView recycleView;

    String id, content_name, domain_name, image_link, catagory, download_link;
    EditText search;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        allData = new ArrayList();
        recycleView = (RecyclerView) findViewById(R.id.recycleview);
        recycleView.setHasFixedSize(true);
        adapter = new MyAdapter(this, allData);
        recycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycleView.setAdapter(adapter);

        search = (EditText) findViewById(R.id.editText_InputSearch);
        Button b=(Button)findViewById(R.id.button);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = search.getText().toString().trim();
                getMovie(s);
            }
        });

        // getdata1("fis");


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getMovie(final String dataname) {
        spinner.setVisibility(View.VISIBLE);
        allData.clear();
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "http://bluedeserts.com/myand/get_movie.php", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String data) {
                try {
                    phraseData(data);
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("moviename", dataname);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void phraseData(String response) throws ParseException, JSONException {

        JSONObject jsonObject = new JSONObject(response);
        System.out.println("lll" + jsonObject);

        JSONArray jr;
        JSONObject object;
        try {

            jr = jsonObject.getJSONArray("results");

            for (int i = 0; i < jr.length(); i++) {

                bean subcategorybean = new bean();

                object = jr.getJSONObject(i);

                String id = object.getString("id");
                String content_name = object.getString("content_name");
                String domain_name = object.getString("domain_name");
                String image_link = object.getString("image_link");
                String catagory = object.getString("catagory");
                String download_link = object.getString("download_link");

                System.out.println("content_name" + content_name + "domain_name" + domain_name + "image_link" + image_link + "catagory" + catagory + "download_link" + download_link);
                subcategorybean.setId(id);
                subcategorybean.setContent_name(content_name);
                subcategorybean.setDomain_name(domain_name);
                subcategorybean.setImage_link(image_link);
                subcategorybean.setCatagory(catagory);
                subcategorybean.setDownload_link(download_link);

                allData.add(subcategorybean);
            }
            adapter.notifyDataSetChanged();
            spinner.setVisibility(View.GONE);
            // Toast.makeText(getApplicationContext(),allSampleData,Toast.LENGTH_LONG).show();
            System.out.println("objjj" + allData);
        } catch (JSONException e) {
            e.printStackTrace();
        }


       /* JSONParser parser = new JSONParser();
        String res = response.toString();
        System.out.println("response" + res);
      //  JSONArray jr;
        JSONObject object = null;

        Object obj = parser.parse(response);
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray jr = jsonObject.getJSONArray("results");
        System.out.println(jsonObject);
        // JSONArray js=jsonObject.getJSONArray("data");
        System.out.println("jsonObject" + jsonObject);*/

    }

/*

    public void getdata1(String dataname) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("moviename", "fish")
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://bluedeserts.com/myand/get_movie.php")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString;
                String errorMessage = null;
                String transactionID = null;
                responseString = response.body().string();
                response.body().close();
                System.out.println(response);
                try {
                    JSONObject responseObject = new JSONObject(responseString);
                    bean bean = new bean();
                    if (responseObject.has("error")) {
                        // errorMessage = responseObject.getString("error");
                    } else {
                        id = responseObject.getString("id");
                        content_name = responseObject.getString("content_name");
                        domain_name = responseObject.getString("domain_name");
                        image_link = responseObject.getString("image_link");
                        catagory = responseObject.getString("catagory");
                        download_link = responseObject.getString("download_link");
                        bean.setId(id);
                        bean.setContent_name(content_name);
                        bean.setDomain_name(domain_name);
                        bean.setImage_link(image_link);
                        bean.setCatagory(catagory);
                        bean.setDownload_link(download_link);

                        allData.add(bean);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // errorMessage = "Failed to fetch Order tokens";
                }
                final String finalTransactionID = transactionID;

                //FianlFinalTransactionID = finalTransactionID;
*/
/*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("aaa" + accessToken+"   "+ finalTransactionID);
                        createOrder(accessToken, finalTransactionID);
                    }
                });*//*

            }
        });


    }

*/

}
