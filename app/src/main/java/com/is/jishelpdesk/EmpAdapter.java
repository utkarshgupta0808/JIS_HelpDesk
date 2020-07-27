package com.is.jishelpdesk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EmpAdapter extends FirestoreRecyclerAdapter<EmpModel, EmpAdapter.MyViewHolder> {


    public EmpAdapter(@NonNull FirestoreRecyclerOptions<EmpModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final EmpAdapter.MyViewHolder holder, final int position, @NonNull final EmpModel model) {


        final String[] assignName = new String[1];
        holder.eId.setText(""+model.getEmpid());
        holder.name.setText(""+model.getName());
        holder.number.setText(""+model.getNumber());
        holder.cardView.setVerticalScrollBarEnabled(true);
        final long[] countActive = {0};
        final long[] countTotal = {0};
        final DocumentReference[] documentReference = new DocumentReference[1];
        final String documentId = getSnapshots().getSnapshot(position).getId();
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Employee").document(documentId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    countActive[0] = Objects.requireNonNull(documentSnapshot.getLong("cActive"));
                    countTotal[0]=Objects.requireNonNull(documentSnapshot.getLong("ctotal"));
                }
            }
        });


        holder.assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.assign.getContext(), "Complaint Assigned to "+model.getName()+" Successfully", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(holder.assign.getContext(),ComplaintAdminActivity.class);
//                intent.putExtra("back",true);







                final long tokenId=AssignEmpActivity.tokenId;
                final String name=AssignEmpActivity.name;
                String address=AssignEmpActivity.address;
                String mobile=AssignEmpActivity.number;
                String complaint=AssignEmpActivity.complaint;
                String date=AssignEmpActivity.date;





                Map<String,Object>user=new HashMap<>();
                user.put("tokenId", tokenId);
                user.put("name",name );
                user.put("mobile", mobile);
                user.put("address",address);
                user.put("complaint", complaint);
                user.put("date", date);


                firebaseFirestore.collection("Employee").document(documentId)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                         @Override
                         public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                             if (task.isSuccessful()) {
                                 DocumentSnapshot documentSnapshot = task.getResult();
                                 assert documentSnapshot != null;
                                 assignName[0] = documentSnapshot.getString("name");
                             }
                         }
                     });



                documentReference[0] =firebaseFirestore.collection("Employee").document(documentId);
                documentReference[0].collection("Complaint")
                        .add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        firebaseFirestore.collection("Complaint")
                                .whereEqualTo("tokenId",tokenId)
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                firebaseFirestore.collection("Complaint").document(queryDocumentSnapshots.getDocuments().get(0).getId())
                                        .update("status","Assigned to "+assignName[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                firebaseFirestore.collection("Employee").document(documentId)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            countActive[0] = Objects.requireNonNull(documentSnapshot).getLong("cActive");
                            countActive[0]++;
                            countTotal[0] =documentSnapshot.getLong("ctotal");
                            countTotal[0]++;
                            final Map<String, Object> count=new HashMap<>();
                            count.put("cActive", countActive[0]);
                            count.put("ctotal", countTotal[0]);

                            firebaseFirestore.collection("Employee").document(documentId)
                                    .update(count).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){


                                    }
                                    else {

                                    }
                                }
                            });
                        }
                    }
                });






//                holder.assign.getContext().startActivity(intent);
                ((Activity)holder.assign.getContext()).finish();



            }
        });
        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                            Intent intent=new Intent(holder.assign.getContext(), EmpProfileActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putLong("empid", model.getEmpid());
                            bundle.putString("name", model.getName());
                            bundle.putString("address", model.getAddress());
                            bundle.putString("pan", model.getPan());
                            bundle.putString("number", model.getNumber());
                            bundle.putString("aadhaar", model.getAadhaar());
                            bundle.putLong("cActive",countActive[0]);
                            bundle.putLong("cTotal", countTotal[0]);
                            bundle.putBoolean("stat",true);
                            intent.putExtras(bundle);
                            holder.viewProfile.getContext().startActivity(intent);



            }
        });
    }



    @NonNull
    @Override
    public EmpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_emp, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eId;
        TextView name;
        TextView number;
        TextView assign;
        TextView viewProfile;
        CardView cardView;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eId = itemView.findViewById(R.id.empid);
            name = itemView.findViewById(R.id.ename);
            number = itemView.findViewById(R.id.date);
            assign=itemView.findViewById(R.id.assign);
            viewProfile=itemView.findViewById(R.id.view_profile);
            cardView=itemView.findViewById(R.id.card_complaint);


        }
    }
}


