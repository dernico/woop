package com.client.woop.woop.fragments.settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.client.woop.woop.R;
import com.client.woop.woop.fragments.interfaces.ISettingsView;
import com.client.woop.woop.controller.SettingsController;
import com.client.woop.woop.fragments.BaseFragment;
import com.client.woop.woop.models.PersonModel;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends BaseFragment implements ISettingsView{

    private SettingsController _controller;
    private EditText _serviceAdressText;
    private TextView _accountNameText;
    private TextView _displayNameText;
    private Button _resetUserButton;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btn = (Button) v.findViewById(R.id.fragment_settings_search_woop_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.searchServer();
            }
        });

        _serviceAdressText = (EditText) v.findViewById(R.id.fragment_settings_serviceAddress_text);
        _accountNameText = (TextView) v.findViewById(R.id.fragment_settings_user_accountName);
        _displayNameText = (TextView) v.findViewById(R.id.fragment_settings_user_displayName);
        _resetUserButton = (Button) v.findViewById(R.id.fragment_settings_reset_user_button);
        _resetUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.resetUserData();
            }
        });

        _controller = new SettingsController(this, woopServer(), navigation(), googleData());

        return v;
    }


    @Override
    public void showProgressBar(String message) {
        String title = getString(R.string.Loading);
        super.showProgressbar(title, message);
    }

    @Override
    public void hideProgressBar() {
        super.hideProgressbar();
    }

    @Override
    public void setServiceAddress(String address) {
        _serviceAdressText.setText(address);
    }

    @Override
    public void setPersonInfo(PersonModel model) {
        _accountNameText.setText(model.getAccountName());
        _displayNameText.setText(model.getDisplayName());
    }
}
