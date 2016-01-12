package com.android.bloodhelp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bloodhelp.R;
import com.android.bloodhelp.backend.PersonProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by gspl on 12-01-2016.
 */
public class UserListingAdapter extends RecyclerView.Adapter<UserListingAdapter.ViewHolder>
{
    private List<PersonProfile> mPersonProfile = new ArrayList<>();
    private Context context;

    public UserListingAdapter(Context context, List<PersonProfile> personProfileList){
        this.context = context;
        mPersonProfile = new ArrayList<>(personProfileList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        PersonProfile personProfile = mPersonProfile.get(position);
        Picasso.with(context)
                .load(personProfile.getProfilePic())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).into(holder.userProfileImage);
        holder.username.setText(personProfile.getUsername());
        holder.bloodType.setText(personProfile.getBloodGroup());
    }

    @Override
    public int getItemCount()
    {
        return mPersonProfile.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        @Bind(R.id.user_profile_img)
        ImageView userProfileImage;
        @Bind(R.id.username)
        TextView username;
        @Bind(R.id.distance)
        TextView distance;
        @Bind(R.id.bloodType)
        TextView bloodType;

        public ViewHolder(View view)
        {
            super(view);
            //viewRoot = view;
            ButterKnife.bind(this, view);
        }
    }
}
