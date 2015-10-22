package com.example.vijayshankar.vijayapptimer2;

/**
 * Created by VijayShankar on 9/25/2015.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetupFragment extends Fragment {
    EditText setupTouchTimer;
    EditText setupDuration1;
    SetupFragmentListener activityCommander;


    public interface SetupFragmentListener {
        public void SetupInfo (Integer TouchTimer, Integer Duration);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            activityCommander = (SetupFragmentListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setup_fragment,container,false);
        setupTouchTimer =(EditText) view.findViewById(R.id.setupTimer);
        setupDuration1 =(EditText) view.findViewById(R.id.Duration);
        final Button button = (Button) view.findViewById(R.id.SetupOKButton);

        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonClicked(v);
                    }
                }
        );
        return view;
    }

    //Calls this when the button is clicked

    public void buttonClicked (View view){
        Integer setupTimer = 10;
        Integer setupDuration = 15;

        setupTimer = Integer.valueOf(setupTouchTimer.getText().toString());
        setupDuration = Integer.valueOf(setupDuration1.getText().toString());
        activityCommander.SetupInfo(setupTimer,setupDuration);
    }



}
