package sols.develiz.ngodatacapture.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.thekhaeng.pushdownanim.PushDownAnim;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sols.develiz.ngodatacapture.R;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class StrategiesAdapter extends RecyclerView.Adapter<StrategiesAdapter.ViewHolder> {
    public Context context;


    ArrayList<String> data;
    int selectedPosition = -1;
    StrategiesAdapter.strategyCallBackListener strategyCallBackListener;

    public StrategiesAdapter(ArrayList<String> data, Context context, StrategiesAdapter.strategyCallBackListener strategyCallBackListener) {
        super();
        this.data = data;
        this.context = context;

        this.strategyCallBackListener= strategyCallBackListener;


    }


    @Override
    public StrategiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.strategy_adapter_row, parent, false);
        StrategiesAdapter.ViewHolder viewHolder = new StrategiesAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final StrategiesAdapter.ViewHolder holder, final int position) {
        final String objective= data.get(position);
        try {
            JSONObject obj=new JSONObject(objective);

            final String  strategies=obj.getString("strategy");

            holder.tv_strategy.setText(strategies);
            PushDownAnim.setPushDownAnimTo(holder.itemView).setScale(MODE_SCALE, 0.89f).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    strategyCallBackListener.appointmentClickCallback(strategies);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }


    @Override
    public int getItemCount() {

        if (data != null) {
            return data.size();

        } else {
            return 0;
        }

    }




    class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView tv_strategy;

        public ViewHolder(View itemView) {
            super(itemView);


            tv_strategy=itemView.findViewById(R.id.tv_strategy);
        }
    }

    public interface strategyCallBackListener {
        void appointmentClickCallback(String strategies);
    }

}
