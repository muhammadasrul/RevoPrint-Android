package com.asrul.revoprint.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.asrul.revoprint.R;
import com.asrul.revoprint.SessionManager;
import com.asrul.revoprint.ui.login.LoginActivity;

public class AccountFragment extends Fragment {

    SessionManager sessionManager;
    TextView tvId, tvName, tvEmail;
    String id, name, email;
    Button btnLogout;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvId = view.findViewById(R.id.tv_id);
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        btnLogout = view.findViewById(R.id.btn_logout);

        sessionManager = new SessionManager(getContext());

        id = sessionManager.getUserDetail().get(SessionManager.ID);
        name = sessionManager.getUserDetail().get(SessionManager.NAME);
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL);

        tvId.setText(id);
        tvName.setText(name);
        tvEmail.setText(email);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutSession();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}