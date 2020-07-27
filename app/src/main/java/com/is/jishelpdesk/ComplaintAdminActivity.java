package com.is.jishelpdesk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Query.Direction;

import java.util.Objects;

public class ComplaintAdminActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton button;
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ComplaintAdminAdapter complaintAdminAdapter;

    //    DocumentReference documentReference;
//    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_admin);

        toolbar=findViewById(R.id.toolbar);
        button=findViewById(R.id.btn_logout);
        mAuth=FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.complaint_recyclerview);
//        userId = getIntent().getStringExtra("user_id");
        firebaseFirestore= FirebaseFirestore.getInstance();



//        documentReference = firebaseFirestore.collection("Complaint").document("userId");
        Query query= firebaseFirestore.collection("Complaint").orderBy("tokenId", Direction.DESCENDING);
        FirestoreRecyclerOptions<ComplaintModel> options= new FirestoreRecyclerOptions.Builder<ComplaintModel>()
                .setQuery(query, ComplaintModel.class).build();
        complaintAdminAdapter=new ComplaintAdminAdapter(options);
        complaintAdminAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(complaintAdminAdapter);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });
    }
    private void logOut() {
        mAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin() {
        Intent intent = new Intent(ComplaintAdminActivity.this, AdminLoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(ComplaintAdminActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
    }
    void showLogoutDialog(){
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view1=layoutInflater.inflate(R.layout.alert_dialog,null);
        Button yesButton=view1.findViewById(R.id.btn_yes);
        Button cancelButton=view1.findViewById(R.id.btn_cancel);

        final AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setView(view1)
                .create();
        alertDialog.show();

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        complaintAdminAdapter.stopListening();

//        if(!getIntent().getBooleanExtra("back", true)){
//            finish();
//            System.exit(0);
//        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        complaintAdminAdapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,WelcomePageActivity.class);
        finish();
        startActivity(intent);
    }

}