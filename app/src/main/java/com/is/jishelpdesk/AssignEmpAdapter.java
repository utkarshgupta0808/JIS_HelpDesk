package com.is.jishelpdesk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class AssignEmpAdapter extends FirestoreRecyclerAdapter<EmpModel, AssignEmpAdapter.MyViewHolder> {


    final long[] assignEmpId = new long[1];
    final long[] prevEmpId = new long[1];

    private ProgressDialog progressDialog;



    public AssignEmpAdapter(@NonNull FirestoreRecyclerOptions<EmpModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final AssignEmpAdapter.MyViewHolder holder, final int position, @NonNull final EmpModel model) {


        final long[] countActive = {0};
        final long[] countTotal = {0};
        final DocumentReference[] documentReference = new DocumentReference[1];

        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        final String[] assignName = new String[1];
        holder.eId.setText(""+model.getEmpid());
        holder.name.setText(""+model.getName());
        holder.number.setText(""+model.getNumber());
        holder.cardView.setVerticalScrollBarEnabled(true);
        Glide.with(holder.photo).load(model.getPhoto()).into(holder.photo);



        firebaseFirestore.collection("Employee").document(getSnapshots().getSnapshot(position).getId())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    assert documentSnapshot != null;
                    countActive[0] = Objects.requireNonNull(documentSnapshot.getLong("cActive"));
                    countTotal[0]=Objects.requireNonNull(documentSnapshot.getLong("ctotal"));
                }
            }
        });

        holder.cardPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",model.getPhoto());
                bundle1.putString("photoDes",model.getName());
                holder.cardPhoto.getContext().startActivity(new Intent(holder.cardPhoto.getContext(),PhotoPreviewActivity.class).putExtras(bundle1));
            }
        });
//
//        holder.assign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(holder.assign.getContext(), "Complaint Assigned to "+model.getName()+" Successfully", Toast.LENGTH_SHORT).show();
////                Intent intent=new Intent(holder.assign.getContext(),ComplaintAdminActivity.class);
////                intent.putExtra("back",true);
//
//
//
//
//
//
//
//                final long tokenId=AssignEmpActivity.tokenId;
//                final String name=AssignEmpActivity.name;
//                String address=AssignEmpActivity.address;
//                String mobile=AssignEmpActivity.number;
//                String complaint=AssignEmpActivity.complaint;
//                String date=AssignEmpActivity.date;
//
//
//
//
//
//                Map<String,Object>user=new HashMap<>();
//                user.put("tokenId", tokenId);
//                user.put("name",name );
//                user.put("mobile", mobile);
//                user.put("address",address);
//                user.put("complaint", complaint);
//                user.put("date", date);
//
//
//                firebaseFirestore.collection("Employee").document(getSnapshots().getSnapshot(position).getId())
//                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot documentSnapshot = task.getResult();
//                            assert documentSnapshot != null;
//                            assignName[0] = documentSnapshot.getString("name");
//                        }
//                    }
//                });
//
//
//
//                documentReference[0] = firebaseFirestore.collection("Employee").document(getSnapshots().getSnapshot(position).getId());
//                documentReference[0].collection("Complaint")
//                        .add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//
//                        firebaseFirestore.collection("Complaint")
//                                .whereEqualTo("tokenId",tokenId)
//                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                firebaseFirestore.collection("Complaint").document(queryDocumentSnapshots.getDocuments().get(0).getId())
//                                        .update("status","Assigned to "+assignName[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//
//                                    }
//                                });
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                            }
//                        });
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//
//                firebaseFirestore.collection("Employee").document(getSnapshots().getSnapshot(position).getId())
//                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()){
//                            DocumentSnapshot documentSnapshot = task.getResult();
//                            countActive[0] = Objects.requireNonNull(documentSnapshot).getLong("cActive");
//                            countActive[0]++;
//                            countTotal[0] =documentSnapshot.getLong("ctotal");
//                            countTotal[0]++;
//                            final Map<String, Object> count=new HashMap<>();
//                            count.put("cActive", countActive[0]);
//                            count.put("ctotal", countTotal[0]);
//
//                            firebaseFirestore.collection("Employee").document(getSnapshots().getSnapshot(position).getId())
//                                    .update(count).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()){
//
//
//                                    }
//                                    else {
//
//                                    }
//                                }
//                            });
//                        }
//                    }
//                });
//
//
//
//
//
//
////                holder.assign.getContext().startActivity(intent);
//                ((Activity)holder.assign.getContext()).finish();
//
//
//
//            }
//        });


        holder.assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Toast.makeText(holder.assign.getContext(), "Complaint Assigned to "+model.getName()+" Successfully", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(holder.assign.getContext(),ComplaintAdminActivity.class);
//                intent.putExtra("back",true);


//                if (AssignEmpActivity.newAssign){
//
////                    updatePreviousEmployee();
//                    firebaseFirestore.collection("Complaint").whereEqualTo("tokenId",AssignEmpActivity.tokenId)
//                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                        @Override
//                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                            firebaseFirestore.collection("Complaint").document(queryDocumentSnapshots.getDocuments().get(0).getId())
//                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        DocumentSnapshot documentSnapshot = task.getResult();
//                                        assert documentSnapshot != null;
//                                        prevEmpId[0] =documentSnapshot.getLong("assignedEmpId");
//                                        Toast.makeText(holder.assign.getContext(), "prev emp id "+prevEmpId[0], Toast.LENGTH_SHORT).show();
////                            Toast.makeText(holder.assign.getContext(), "hello "+prevEmpId[0], Toast.LENGTH_SHORT).show();
//                                        firebaseFirestore.collection("Employee").whereEqualTo("empid",prevEmpId[0])
//                                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                                            @Override
//                                            public void onSuccess(final QuerySnapshot queryDocumentSnapshots) {
//
//                                                firebaseFirestore.collection("Employee").document(queryDocumentSnapshots.getDocuments().get(0).getId())
//                                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                                    @Override
//                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                                                        long countActive1;
//                                                        countActive1 = Objects.requireNonNull(documentSnapshot).getLong("cActive");
//                                                        countActive1--;
//                                                        long countTotal1;
//                                                        countTotal1 = documentSnapshot.getLong("ctotal");
//                                                        countTotal1--;
//                                                        final Map<String, Object> count1=new HashMap<>();
//                                                        count1.put("cActive", countActive1);
//                                                        count1.put("ctotal", countTotal1);
//
//                                                        firebaseFirestore.collection("Employee").document(queryDocumentSnapshots.getDocuments().get(0).getId())
//                                                                .update(count1).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                            @Override
//                                                            public void onSuccess(Void aVoid) {
//
//
//
//
//                                                            }
//                                                        }).addOnFailureListener(new OnFailureListener() {
//                                                            @Override
//                                                            public void onFailure(@NonNull Exception e) {
////                                                    Toast.makeText(holder.assign.getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        });
//                                                    }
//                                                }).addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
////                                            Toast.makeText(holder.assign.getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                });
//
//                                                final DocumentReference documentReference1=firebaseFirestore.collection("Employee").document(queryDocumentSnapshots.getDocuments().get(0).getId());
//                                                documentReference1.collection("Complaint").whereEqualTo("tokenId",AssignEmpActivity.tokenId )
//                                                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                                                    @Override
//                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots1) {
//                                                        Toast.makeText(holder.assign.getContext(), "tokenId"+AssignEmpActivity.tokenId, Toast.LENGTH_SHORT).show();
//                                                        documentReference1.collection("Complaint").document(queryDocumentSnapshots1.getDocuments().get(0).getId())
//                                                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                            @Override
//                                                            public void onSuccess(Void aVoid) {
//
//                                                            }
//                                                        }).addOnFailureListener(new OnFailureListener() {
//                                                            @Override
//                                                            public void onFailure(@NonNull Exception e) {
////                                                                    Toast.makeText(holder.assign.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        });
//                                                    }
//                                                }).addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
////                                                            Toast.makeText(holder.assign.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                });
//
//
//
//
//
//
//
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
////                                    Toast.makeText(holder.assign.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//
//                                    }
//                                }
//                            });
//
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
////                Toast.makeText(holder.assign.getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }

                LayoutInflater layoutInflater=LayoutInflater.from(holder.assign.getContext());
                View view1=layoutInflater.inflate(R.layout.alert_dialog,null);
                Button yesButton=view1.findViewById(R.id.btn_yes);
                Button cancelButton=view1.findViewById(R.id.btn_cancel);

                final AlertDialog alertDialog=new AlertDialog.Builder(holder.assign.getContext())
                        .setView(view1)
                        .create();
                alertDialog.show();

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        progressDialog = new ProgressDialog(holder.assign.getContext());
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.process_dialog);
                        progressDialog.setCanceledOnTouchOutside(false);
                        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                                android.R.color.transparent
                        );

                        long tokenId=AssignEmpActivity.tokenId;
                        String name=AssignEmpActivity.name;
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


                        firebaseFirestore.collection("Employee").document(getSnapshots().getSnapshot(position).getId())
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    assert documentSnapshot != null;
                                    assignName[0] = documentSnapshot.getString("name");
                                    assignEmpId[0] =documentSnapshot.getLong("empid");
                                }
                            }
                        });



                        documentReference[0] =firebaseFirestore.collection("Employee").document(getSnapshots().getSnapshot(position).getId());
                        documentReference[0].collection("Complaint")
                                .add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {


                                firebaseFirestore.collection("Complaint").whereEqualTo("tokenId",AssignEmpActivity.tokenId).get()
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

//                                        firebaseFirestore.collection("Complaint").document(queryDocumentSnapshots.getDocuments().get(0).getId())
//                                                .update("status","Assigned to "+assignName[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//
//                                                Toast.makeText(holder.assign.getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
//                                            }
//                                        });

                                                firebaseFirestore.collection("Complaint").document(queryDocumentSnapshots.getDocuments().get(0).getId())
                                                        .update("assignedEmpId",assignEmpId[0],"status","Assigned to "+assignName[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Toast.makeText(holder.assign.getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(holder.assign.getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                        firebaseFirestore.collection("Employee").document(getSnapshots().getSnapshot(position).getId())
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

                                    firebaseFirestore.collection("Employee").document(getSnapshots().getSnapshot(position).getId())
                                            .update(count).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            task.isSuccessful();

                                            Toast.makeText(holder.assign.getContext(), "Complaint Assigned Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });




                        progressDialog.dismiss();

                        ((Activity)holder.assign.getContext()).finish();

//                holder.assign.getContext().startActivity(intent);


                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });















            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                            Intent intent=new Intent(holder.cardView.getContext(), AdminEmpProfileActivity.class);
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
                            bundle.putString("photo",model.getPhoto());
                            intent.putExtras(bundle);
                            holder.cardView.getContext().startActivity(intent);



            }
        });
    }


//    private void updatePreviousEmployee() {

//
//    }


    @NonNull
    @Override
    public AssignEmpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_emp, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eId;
        TextView name;
        TextView number;
        TextView assign;
        ImageView photo;
//        TextView viewProfile;
        CardView cardView, cardPhoto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eId = itemView.findViewById(R.id.empid);
            name = itemView.findViewById(R.id.ename);
            number = itemView.findViewById(R.id.date);
            assign=itemView.findViewById(R.id.assign);
//            viewProfile=itemView.findViewById(R.id.view_profile);
            cardView=itemView.findViewById(R.id.card_emp);
            cardPhoto=itemView.findViewById(R.id.photo_card);
            photo=itemView.findViewById(R.id.emp_photo_recycler);

        }
    }
}
