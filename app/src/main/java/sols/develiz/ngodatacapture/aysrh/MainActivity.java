package sols.develiz.ngodatacapture.aysrh;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import sols.develiz.ngodatacapture.HomeActivity;
import sols.develiz.ngodatacapture.R;
import sols.develiz.ngodatacapture.adapter.ObjectiveAdapter;
import sols.develiz.ngodatacapture.base.BaseActivity;
import sols.develiz.ngodatacapture.model.aysrh.Objective;

public class MainActivity extends BaseActivity implements View.OnClickListener,ObjectiveAdapter.objectiveCallBackListener {
    private ArrayList<String> objectiveArraylist;
    private RecyclerView recyclerViewVisitsObject;
    private ObjectiveAdapter adapter;


    @Override
    public void initActivity(Bundle b) {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Data Collection Tool");
        recyclerViewVisitsObject = findViewById(R.id.recyclerObjective);
        recyclerViewVisitsObject.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        objectiveArraylist = new ArrayList<>();
        recyclerViewVisitsObject.setLayoutManager(layoutManager);

        JSONArray jArray = Objective.getData();
        if (jArray != null) {
            for (int i=0;i<jArray.length();i++){
                try {
                    objectiveArraylist.add(jArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        adapter = new ObjectiveAdapter(objectiveArraylist, this, this);
        recyclerViewVisitsObject.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int initView() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    @Override
    public void appointmentClickCallback(String objective,String outcome) {
         Intent intent=new Intent(MainActivity.this, ExpectedOutcomeActivity.class);
         intent.putExtra("NGO_OBJECTIVE",objective);
         intent.putExtra("NGO_OUTCOME",outcome);
        startActivity(intent);
        finish();

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        finish();
    }
}