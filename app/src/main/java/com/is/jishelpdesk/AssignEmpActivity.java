package com.is.jishelpdesk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AssignEmpActivity extends AppCompatActivity {

//    Toolbar toolbar;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    AssignEmpAdapter assignEmpAdapter;
    static String  name, date, number, status, complaint, address;
    static long tokenId;
    static boolean newAssign;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_emp);

//        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.emp_recyclerview);
        btnBack=findViewById(R.id.btn_back);



        firebaseAuth=FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.emp_recyclerview);
        firebaseFirestore= FirebaseFirestore.getInstance();

        Query query= firebaseFirestore.collection("Employee").orderBy("empid", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<EmpModel> options= new FirestoreRecyclerOptions.Builder<EmpModel>()
                .setQuery(query, EmpModel.class).build();
        assignEmpAdapter =new AssignEmpAdapter(options);
        assignEmpAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(assignEmpAdapter);

        Bundle extra=getIntent().getExtras();

        assert extra != null;
        tokenId=extra.getLong("tokenId");
        name=extra.getString("name");
        date=extra.getString("date");
        status=extra.getString("status");
        complaint=extra.getString("complaint");
        address=extra.getString("address");
        number=extra.getString("number1");
        newAssign=extra.getBoolean("newAssignment");

//        Toast.makeText(this,  tokenId+"", Toast.LENGTH_SHORT).show();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AssignEmpActivity.this,ComplaintInfoAdminActivity.class);
                finish();
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(AssignEmpActivity.this,ComplaintInfoAdminActivity.class);
        finish();
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        assignEmpAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        assignEmpAdapter.stopListening();

    }
}
