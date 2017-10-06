package com.example.kanyes.mdbsocials;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import java.util.ArrayList;

/**
 * Created by Kanyes on 9/27/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.CustomViewHolder> {
    private Context context;
    private ArrayList<Social> data;

    public FeedAdapter(Context context, ArrayList<Social> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        Social s = data.get(position);
        holder.eventView.setText(s.getEvent());
        holder.evemailView.setText(s.getEmail());
        holder.num_interestedView.setText("" + s.getNum_interested()+ " interested!");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(s.id + ".png");
        Glide.with(context).using(new FirebaseImageLoader()).load(storageReference).into(holder.imgView);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView eventView;
        ImageView imgView;
        TextView evemailView;
        TextView num_interestedView;

        public CustomViewHolder (View view){
            super(view);
            this.eventView = (TextView) view.findViewById(R.id.eventView);
            this.evemailView = (TextView) view.findViewById(R.id.evemailView);
            this.num_interestedView = (TextView) view.findViewById(R.id.num_interestedView);
            this.imgView = (ImageView) view.findViewById(R.id.evimageView);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Social social = FeedActivity.socials.get(getAdapterPosition());
            Intent intent = new Intent(context, SocialPage.class);
            intent.putExtra("id", social.getId());
            context.startActivity(intent);
        }
    }

}
