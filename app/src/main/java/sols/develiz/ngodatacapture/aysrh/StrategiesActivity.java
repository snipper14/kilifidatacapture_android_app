package sols.develiz.ngodatacapture.aysrh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import sols.develiz.ngodatacapture.R;
import sols.develiz.ngodatacapture.adapter.StrategiesAdapter;
import sols.develiz.ngodatacapture.base.BaseActivity;

public class StrategiesActivity extends BaseActivity implements StrategiesAdapter.strategyCallBackListener {
    private TextView tv_objective2;
    private ArrayList<String> strategyArraylist;
    private RecyclerView recyclerViewStrategies;
    private StrategiesAdapter adapter;
    private String objective,outcome;


    @Override
    public void initActivity(Bundle b) {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Collection Tool");

        Intent intent = getIntent();
        objective = intent.getStringExtra("NGO_OBJECTIVE");
        String strategies = intent.getStringExtra("NGO_STRATEGIES");
        outcome = intent.getStringExtra("NGO_OUTCOME");
        tv_objective2 = findViewById(R.id.tv_objective2);
        tv_objective2.setText(objective);

        TextView tv_outcome2 = findViewById(R.id.tv_outcome2);
        tv_outcome2.setText(outcome);


        recyclerViewStrategies = findViewById(R.id.recyclerStrategy);
        recyclerViewStrategies.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        strategyArraylist = new ArrayList<>();
        recyclerViewStrategies.setLayoutManager(layoutManager);

        JSONArray jArray = null;
        try {
            jArray = new JSONArray(strategies);
            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){

                    strategyArraylist.add(jArray.getString(i));

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new StrategiesAdapter(strategyArraylist, this, this);
        recyclerViewStrategies.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int initView() {
        return R.layout.activity_strategies;
    }

    @Override
    public void onBackPressed() {

        StrategiesActivity.super.onBackPressed();
        startActivity(new Intent(StrategiesActivity.this, MainActivity.class));
        finish();

    }

    @Override
    public void appointmentClickCallback( String strategies) {
             Intent intent=new Intent(StrategiesActivity.this, DataRegistrationActivity.class);
             intent.putExtra("NGO_STRATEGY",strategies);
             intent.putExtra("NGO_OBJECTIVE",objective);
             intent.putExtra("NGO_OUTCOME",outcome);
             startActivity(intent);
             finish();
    }
}