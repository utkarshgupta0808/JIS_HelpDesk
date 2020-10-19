package com.is.jishelpdesk;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminEmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AdminEmployeeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    RecyclerView recyclerView;
    AdminEmpAdapter adminEmpAdapter ;
//    static String  name, date, number, status, complaint, address;
//    static long tokenId;
//    static boolean newAssign;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminEmployeeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminEmployeeFragment newInstance(String param1, String param2) {
        AdminEmployeeFragment fragment = new AdminEmployeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AdminEmployeeFragment() {
        // Required empty public constructor
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
        View view=inflater.inflate(R.layout.fragment_admin_employee, container, false);

        recyclerView=view.findViewById(R.id.emp_recyclerview);


        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

        Query query= firebaseFirestore.collection("Employee").orderBy("empid", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<EmpModel> options= new FirestoreRecyclerOptions.Builder<EmpModel>()
                .setQuery(query, EmpModel.class).build();
        adminEmpAdapter =new AdminEmpAdapter(options);
        adminEmpAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adminEmpAdapter);

//        Bundle extra=getIntent().getExtras();
//
//        assert extra != null;
//        tokenId=extra.getLong("tokenId");
//        name=extra.getString("name");
//        date=extra.getString("date");
//        status=extra.getString("status");
//        complaint=extra.getString("complaint");
//        address=extra.getString("address");
//        number=extra.getString("number1");
//        newAssign=extra.getBoolean("newAssignment");

        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        adminEmpAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adminEmpAdapter.stopListening();

    }





}