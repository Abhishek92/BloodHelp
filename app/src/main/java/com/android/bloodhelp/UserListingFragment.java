package com.android.bloodhelp;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.bloodhelp.adapter.UserListingAdapter;
import com.android.bloodhelp.backend.PersonProfile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserListingFragment extends BaseFragment
{

    @Bind(R.id.frag_user_listing_recycler_view)
    RecyclerView mUserListingRecycler;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    private LinearLayoutManager mLinearLayoutManager;
    private List<PersonProfile> mPersonProfileList = new ArrayList<>();

    public UserListingFragment()
    {
        // Required empty public constructor
    }


    @Override
    public void onFragmentReady(View view)
    {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mUserListingRecycler.setLayoutManager(mLinearLayoutManager);

        getUsersListAccordingToDistance();
    }

    @Override
    public int getFragmentLayout()
    {
        return R.layout.fragment_user_listing;
    }

    private void getUsersListAccordingToDistance()
    {

        double latitude = Double.parseDouble(BloodHelpApp.getSavePrefsInstance(getActivity()).getCurrentLatitude());
        double longitude = Double.parseDouble(BloodHelpApp.getSavePrefsInstance(getActivity()).getCurrentLongitude());
        ParseGeoPoint geoPoint = new ParseGeoPoint(latitude, longitude);
        ParseQuery<PersonProfile> query = ParseQuery.getQuery(PersonProfile.class);
        query.whereWithinKilometers("location", geoPoint, 10.0);
        mProgressBar.setVisibility(View.VISIBLE);
        query.findInBackground(new FindCallback<PersonProfile>()
        {
            @Override
            public void done(List<PersonProfile> objects, ParseException e)
            {
                if(e == null)
                {
                    mPersonProfileList = new ArrayList<PersonProfile>(objects);
                    mProgressBar.setVisibility(View.GONE);
                    System.out.println(objects);
                    setUserListingAdapter();
                }
                else
                {
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUserListingAdapter()
    {
        UserListingAdapter adapter = new UserListingAdapter(getActivity(), mPersonProfileList);
        mUserListingRecycler.setAdapter(adapter);
    }
}
