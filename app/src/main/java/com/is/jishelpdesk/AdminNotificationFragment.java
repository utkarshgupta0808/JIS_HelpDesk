package com.is.jishelpdesk;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminNotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminNotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView notificationTitle, notificationBody;
//    private String notiTile, notiBody;
    Button sendNotification;

    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";


    public AdminNotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminNotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminNotificationFragment newInstance(String param1, String param2) {
        AdminNotificationFragment fragment = new AdminNotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view=inflater.inflate(R.layout.fragment_admin_notification, container, false);

        sendNotification =view.findViewById(R.id.send_noti);
        notificationTitle =view.findViewById(R.id.noti_title);
        notificationBody =view.findViewById(R.id.noti_text);
        mRequestQue = Volley.newRequestQueue(view.getContext());
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        if (getActivity().getIntent().hasExtra("titleNoti")){

            Intent intent = new Intent(view.getContext(),ReceiveNotificationActivity.class);
            intent.putExtra("notiTitle",notificationTitle.getText().toString().trim());
            intent.putExtra("notiBody",notificationBody.getText().toString().trim());
            startActivity(intent);
        }





        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title= notificationTitle.getText().toString().trim();
                String body= notificationBody.getText().toString().trim();


                if (TextUtils.isEmpty(title)){
                    notificationTitle.setError("Title is Required");
                }

                else if (TextUtils.isEmpty(body)){
                    notificationBody.setError("Body text is Required");
                }
                else {
                    sendNotification();
                }

            }
        });

        return view;
    }

    private void sendNotification() {

        JSONObject json = new JSONObject();
        try {
            json.put("to","/topics/"+"news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title",notificationTitle.getText().toString());
            notificationObj.put("body",notificationBody.getText().toString());

            JSONObject extraData = new JSONObject();
            extraData.put("notiTitle",notificationTitle.getText().toString().trim());
            extraData.put("notiBody",notificationBody.getText().toString().trim());



            json.put("notification",notificationObj);
            json.put("data",extraData);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("MUR", "onResponse: ");
                            Toast.makeText(getContext(), "Notification Sent", Toast.LENGTH_SHORT).show();
                            notificationTitle.setText("");
                            notificationBody.setText("");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("MUR", "onError: "+error.networkResponse);
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAsy4XjSk:APA91bEL71sXyApLjZYh1VV8xgZyOK6r1tphadLRD35eb8mBA0COKI5Ddbl6hUj2jO3jSOTlt0yvDeGSzm6y5oZT50xx6F9kfu0e_jEAbFgelEMASBfWKtUnhYJFmRFluxyeNTiOUnkO");
                    return header;
                }
            };
            mRequestQue.add(request);
        }
        catch (JSONException e)

        {
            e.printStackTrace();
        }
    }
}