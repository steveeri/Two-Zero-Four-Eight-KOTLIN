package com.summerland.android.twozerofoureightapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.i("Logm", "Inside on create main activity 2");
        MyPassingObject mpo = (MyPassingObject) getIntent().getSerializableExtra("my_first_object_passing");
        Log.i("Logm", mpo.toString());
        Log.i("Logm", mpo.getMyStrName());

        TextView tv = (TextView) findViewById(R.id.mesg_text_view);
        tv.setTextColor(Color.BLUE);
        tv.setText(mpo.getMyStrName());

        this.createAndAddFragment();
    }

    private void createAndAddFragment() {
        Log.i("Logm", "Inside createAndAddFragment in main activity 2");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MainActivity2Fragment frag = new MainActivity2Fragment();
        //ft.setCustomAnimations(FragmentTransaction.TRANSIT_FRAGMENT_OPEN, FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.add(R.id.my_rel_layout, frag, "MY_FRAGMENT");
        ft.commit();
        Log.i("Logm", "Inside createAndAddFragment after commit");
    }

    public void onClick(View view) {
        this.finish();
    }
}
