package sols.develiz.ngodatacapture.aysrh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import sols.develiz.ngodatacapture.R;
import sols.develiz.ngodatacapture.adapter.OutcomeAdapter;
import sols.develiz.ngodatacapture.base.BaseActivity;

public class ExpectedOutcomeActivity extends BaseActivity implements View.OnClickListener, OutcomeAdapter.outcomeCallBackListener {
    private TextView tv_objective;
    private ArrayList<String> outcomeArraylist;
    private RecyclerView recyclerViewOutcome;
    private OutcomeAdapter adapter;
    private String objective;



    @Override
    public void initActivity(Bundle b) {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Collection Tool");

        Intent intent = getIntent();
        String outcome = intent.getStringExtra("NGO_OUTCOME");
        objective = intent.getStringExtra("NGO_OBJECTIVE");
        tv_objective = findViewById(R.id.tv_objective);
        tv_objective.setText(objective);


        recyclerViewOutcome = findViewById(R.id.recyclerOutcome);
        recyclerViewOutcome.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        outcomeArraylist = new ArrayList<>();
        recyclerViewOutcome.setLayoutManager(layoutManager);

        JSONArray jArray = null;
        try {
            jArray = new JSONArray(outcome);
            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){

                    outcomeArraylist.add(jArray.getString(i));

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new OutcomeAdapter(outcomeArraylist, this, this);
        recyclerViewOutcome.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public int initView() {
        return R.layout.activity_expected_outcome;
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onBackPressed() {

        ExpectedOutcomeActivity.super.onBackPressed();
        startActivity(new Intent(ExpectedOutcomeActivity.this, MainActivity.class));
        finish();

    }

    @Override
    public void appointmentClickCallback(String outcome, String strategies) {
        Intent intent=new Intent(ExpectedOutcomeActivity.this, StrategiesActivity.class);
        intent.putExtra("NGO_OUTCOME",outcome);
        intent.putExtra("NGO_STRATEGIES",strategies);
        intent.putExtra("NGO_OBJECTIVE",objective);
        startActivity(intent);
        finish();
    }
}