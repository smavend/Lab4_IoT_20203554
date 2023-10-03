package com.example.lab4_appiot_20203554.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_appiot_20203554.R;
import com.example.lab4_appiot_20203554.entity.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AcelerometroAdapter extends RecyclerView.Adapter<AcelerometroAdapter.AcelerometroViewHolder>{

    private List<Result> listaResults;
    private Context context;
    @NonNull
    @Override
    public AcelerometroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cards_acel,parent,false);
        return new AcelerometroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcelerometroViewHolder holder, int position) {
        Result r = listaResults.get(position);
        holder.result = r;

        TextView name = holder.itemView.findViewById(R.id.textName);
        TextView gender = holder.itemView.findViewById(R.id.textGender);
        TextView city = holder.itemView.findViewById(R.id.textCity);
        TextView country = holder.itemView.findViewById(R.id.textCountry);
        TextView phone = holder.itemView.findViewById(R.id.textPhone);
        TextView email = holder.itemView.findViewById(R.id.textEmail);
        ImageView pic = holder.itemView.findViewById(R.id.foto);

        name.setText(r.getName().getFullName());
        gender.setText("Género: "+r.getGender());
        city.setText("Ciudad: "+r.getLocation().getCity());
        country.setText("País: "+r.getLocation().getCountry());
        phone.setText("Phone: "+r.getPhone());
        email.setText("Correo: "+r.getEmail());
        Picasso.with(context).load(r.getPicture().getLarge()).into(pic);
    }

    @Override
    public int getItemCount() {
        return listaResults.size();
    }

    public class AcelerometroViewHolder extends RecyclerView.ViewHolder{
        Result result;
        public AcelerometroViewHolder(@NonNull View itemView){
            super(itemView);
            Button button = itemView.findViewById(R.id.closeButton);
            button.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listaResults.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }
    public List<Result> getLista(){
        return listaResults;
    }
    public void setLista(List<Result> listaResults){
        this.listaResults = listaResults;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context){
        this.context = context;
    }
}
