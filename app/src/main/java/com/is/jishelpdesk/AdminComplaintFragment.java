package com.is.jishelpdesk;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminComplaintFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    RecyclerView recyclerView;

    ComplaintAdminAdapter complaintAdminAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminComplaintFragment newInstance(String param1, String param2) {
        AdminComplaintFragment fragment = new AdminComplaintFragment();
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
    public void onStop() {
        super.onStop();
        complaintAdminAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        complaintAdminAdapter.startListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_admin_complaint, container, false);




        recyclerView=view.findViewById(R.id.complaint_recyclerview);
//        userId = getIntent().getStringExtra("user_id");

        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();





//        documentReference = firebaseFirestore.collection("Complaint").document("userId");
        Query query= firebaseFirestore.collection("Complaint").orderBy("tokenId", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ComplaintModel> options= new FirestoreRecyclerOptions.Builder<ComplaintModel>()
                .setQuery(query, ComplaintModel.class).build();
        complaintAdminAdapter=new ComplaintAdminAdapter(options);
        complaintAdminAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(complaintAdminAdapter);


        return view;
    }


}