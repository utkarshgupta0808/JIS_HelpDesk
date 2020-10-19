package com.is.jishelpdesk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ComplaintAdminAdapter extends FirestoreRecyclerAdapter<ComplaintModel, ComplaintAdminAdapter.MyViewHolder> {

    public ComplaintAdminAdapter(@NonNull FirestoreRecyclerOptions<ComplaintModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull final ComplaintModel model) {
        holder.tokenId.setText("" +model.getTokenId());
        holder.name.setText(model.getName());
        holder.date.setText(model.getDate());
        holder.status.setText(model.getStatus());
        holder.cardView.setVerticalScrollBarEnabled(true);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.cardView.getContext(), ComplaintInfoAdminActivity.class);
                Bundle bundle=new Bundle();
                bundle.putLong("tokenId", model.getTokenId());
                bundle.putString("name", model.getName());
                bundle.putString("date", model.getDate());
                bundle.putString("status", model.getStatus());
                bundle.putString("complaint", model.getComplaint());
                bundle.putString("address", model.getAddress());
                bundle.putString("number1", model.getMobile());
                bundle.putString("complaintId", getSnapshots().getSnapshot(position).getId());
                intent.putExtras(bundle);
                holder.cardView.getContext().startActivity(intent);

            }
        });

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_complaint_admin, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tokenId;
        TextView name;
        TextView date;
        TextView status;
//        TextView viewDetails;
        CardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tokenId = itemView.findViewById(R.id.tokenid);
            name = itemView.findViewById(R.id.cname);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
//            viewDetails=itemView.findViewById(R.id.view_details);
            cardView=itemView.findViewById(R.id.card_complaint);


        }
    }
}

