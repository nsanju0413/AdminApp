package realm.vendingmachines.admin.Final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import realm.vendingmachines.admin.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> image=new ArrayList<>();
    private ArrayList<String> item=new ArrayList<>();
    private ArrayList<String> quant=new ArrayList<>();

    public RecyclerViewAdapter(ArrayList<String> image, ArrayList<String> item, ArrayList<String> quant) {
        this.image = image;
        this.item = item;
        this.quant = quant;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.final_recycleritems,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1,textView2;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull @NotNull View itemView) {

            super(itemView);
            imageView=itemView.findViewById(R.id.appOS_imageVw);
            textView1=itemView.findViewById(R.id.itemname);
            textView2=itemView.findViewById(R.id.quantity);
            constraintLayout=itemView.findViewById(R.id.constarint);

        }
    }
}
