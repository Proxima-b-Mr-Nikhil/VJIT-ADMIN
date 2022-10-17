package com.nikhil.vjitadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class feedAdapter extends RecyclerView.Adapter<feedAdapter.ImageViewHolder> {

    private List<feedUpload> mUploads;

    String a;

    feedUpload uploadCurrent;
    public feedAdapter(Context context, List<feedUpload> uploads) {

        mUploads = uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_feeditem, parent, false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int i) {
       uploadCurrent = mUploads.get(i);

        holder.imgholder.setVisibility(View.VISIBLE);
        holder.imageView.setVisibility(View.GONE);



            Picasso.get()

                    .load(uploadCurrent.getImageUrl())
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.imageView.setVisibility(View.VISIBLE);
                        holder.imgholder.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });
        }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView,imgholder,imgdel;
        boolean flag;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view_upload);
            imgholder = itemView.findViewById(R.id.holder);
            imgdel = itemView.findViewById(R.id.delete);

        }

    }
}
