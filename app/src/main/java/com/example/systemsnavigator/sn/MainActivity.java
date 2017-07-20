package com.example.systemsnavigator.sn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private String url;
    private String consulta;
    private JSONArray jsonArray;
    private JSONObject object;
    private Personas persona;
    private ArrayList<Personas> personas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        url = "http://192.168.11.108/TestPage/consulta.php";
        consulta = "?ins_sql=select * from people";
        object = new JSONObject();
        personas = new ArrayList<Personas>();

        RequestQueue busqueda = Volley.newRequestQueue(this);


        StringRequest sr = new StringRequest(Request.Method.GET, url + consulta, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    jsonArray = new JSONArray(response);

                    for (int i=0; i<=jsonArray.length()-1; i++){
                        persona = new Personas();
                        object = jsonArray.getJSONObject(i);

                        persona.setNombre(object.getString("Nombre"));
                        persona.setApellidos(object.getString("Apellido"));
                        persona.setEdad(object.getString("Edad"));

                        personas.add(persona);
                    }

                        tv1.append("Nombre: " + personas.get(0).getNombre() + "\n"
                                + "Apellido: " + personas.get(0).getApellidos() + "\n"
                                + "Edad: " + personas.get(0).getEdad());




                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv1.setText("Esto no va illo " +error );
            }
        });

        busqueda.add(sr);



    }
}
