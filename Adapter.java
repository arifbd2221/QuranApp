package qapp.mangosoft.com.qappproject;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 2/8/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyHolderView> {


    ArrayList<String> verseDetails,verseNo;
    String suraName;
    int suraNo;
    Context context;

    public Adapter(Context context, ArrayList<String> ayats,ArrayList<String> verseNo,String suraName,int suraNo){
        this.context=context;
        this.verseDetails=ayats;
        this.verseNo=verseNo;
        this.suraName=suraName;
        this.suraNo=suraNo;
    }

    @Override
    public MyHolderView onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyHolderView(v);
    }

    @Override
    public void onBindViewHolder(MyHolderView holder, int position) {



                holder.cardView.setCardBackgroundColor(Color.WHITE);
                holder.suraName.setText(suraName);
                holder.suraNo.setText("Sura No:"+suraNo);
                holder.verseNo.setText("VNO:"+verseNo.get(position));
                holder.verse_detail.setText(verseDetails.get(position));

    }

    @Override
    public int getItemCount() {
        return verseDetails.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView verseNo,verse_detail,suraNo,suraName;

        public MyHolderView(View itemView) {
            super(itemView);
            verseNo=itemView.findViewById(R.id.verse_no);
            verse_detail=itemView.findViewById(R.id.verse_details);
            suraNo=itemView.findViewById(R.id.sura_no);
            suraName=itemView.findViewById(R.id.sura_name);
            cardView=itemView.findViewById(R.id.cardview);
        }
    }
}
