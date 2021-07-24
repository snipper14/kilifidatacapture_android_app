package sols.develiz.ngodatacapture.adapter;

import android.content.Context;
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

public class OutcomeAdapter  extends RecyclerView.Adapter<OutcomeAdapter.ViewHolder> {
    public Context context;


    ArrayList<String> data;
    int selectedPosition = -1;
    OutcomeAdapter.outcomeCallBackListener outcomeCallBackListener;

    public OutcomeAdapter(ArrayList<String> data, Context context, OutcomeAdapter.outcomeCallBackListener outcomeCallBackListener) {
        super();
        this.data = data;
        this.context = context;

        this.outcomeCallBackListener= outcomeCallBackListener;


    }


    @Override
    public OutcomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outcome_adapter_row, parent, false);
        OutcomeAdapter.ViewHolder viewHolder = new OutcomeAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OutcomeAdapter.ViewHolder holder, final int position) {
        final String objective= data.get(position);
        try {
            JSONObject obj=new JSONObject(objective);
            final String outcome=obj.getString("outcome");
            final String  strategies=obj.getString("strategies");
            holder.tv_outcome.setText(outcome);
            PushDownAnim.setPushDownAnimTo(holder.itemView).setScale(MODE_SCALE, 0.89f).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    outcomeCallBackListener.appointmentClickCallback(outcome,strategies);
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
        private TextView tv_outcome;

        public ViewHolder(View itemView) {
            super(itemView);


            tv_outcome=itemView.findViewById(R.id.tv_outcome);
        }
    }

    public interface outcomeCallBackListener {
        void appointmentClickCallback(String outcome,String strategies);
    }

}
