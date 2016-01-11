package com.android.bloodhelp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by hp pc on 09-01-2016.
 */
public abstract class BaseFragment extends Fragment {

    private ProgressDialog mProgressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onFragmentReady(view);
    }

    abstract public void onFragmentReady(View view);
    abstract public int getFragmentLayout();

    protected void showProgressDialog(String message)
    {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(message);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    protected void cancelProgressDialog(){
        mProgressDialog.cancel();
    }
}
