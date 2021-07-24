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

public class ObjectiveAdapter  extends RecyclerView.Adapter<ObjectiveAdapter.ViewHolder> {
    public Context context;


    ArrayList<String> data;
    int selectedPosition = -1;
    objectiveCallBackListener objectiveCallBackListener;

    public ObjectiveAdapter(ArrayList<String> data, Context context, objectiveCallBackListener objectiveCallBackListener) {
        super();
        this.data = data;
        this.context = context;

        this.objectiveCallBackListener= objectiveCallBackListener;


    }


    @Override
    public ObjectiveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objective_adapter_row, parent, false);
        ObjectiveAdapter.ViewHolder viewHolder = new ObjectiveAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ObjectiveAdapter.ViewHolder holder, final int position) {
        final String objective= data.get(position);
        try {
            JSONObject obj=new JSONObject(objective);
            final String object=obj.getString("objective");
            final String  expectedOutcome=obj.getString("expected_outcome");
            holder.tv_objective1.setText(object);
            PushDownAnim.setPushDownAnimTo(holder.itemView).setScale(MODE_SCALE, 0.89f).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    objectiveCallBackListener.appointmentClickCallback(object,expectedOutcome);
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
        private TextView tv_objective1;

        public ViewHolder(View itemView) {
            super(itemView);


            tv_objective1=itemView.findViewById(R.id.tv_objective1);
        }
    }

    public interface objectiveCallBackListener {
        void appointmentClickCallback(String objective,String outcome);
    }

}
