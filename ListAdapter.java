package qapp.mangosoft.com.qappproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 2/9/2018.
 */

public class ListAdapter extends RecyclerView.Adapter <ListAdapter.MyViewHolder>{

    private Database database;
    private Context context;

    private ArrayList<String> queslist,ansList,cmntList;
    ArrayList<Integer> suraList,verseList;
    int versNo,suraNo;


    public ListAdapter(Context context,ArrayList<String> queslist){
        this.context=context;
        this.queslist=queslist;

        database=new Database(context);


        suraList=database.getAllSuraNoFaq();
        verseList=database.getAllVerseNoFaq();

        Log.e("queslist",""+queslist.size());
        ansList=database.getAllAns();
        cmntList=database.getAllVerseFaq();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.listeview_item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



        try {
            Log.e("queslist",""+queslist.size());
            holder.ques.setText(queslist.get(position));
            holder.verse_no.setText("Verse No:"+verseList.get(position));
            holder.sura_name.setText(context.getResources().getStringArray(R.array.SuraName)[suraList.get(position)]);
            holder.sura_no.setText("Sura No:"+(suraList.get(position)));
        }catch (IndexOutOfBoundsException i){
            i.printStackTrace();
        }

        holder.ans.setText(cmntList.get(position));
        holder.cmnt.setText(ansList.get(position));
    }

    @Override
    public int getItemCount() {
        return queslist.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView ques,ans,cmnt,verse_no,sura_no,sura_name;


        public MyViewHolder(View itemView) {
            super(itemView);
            ques=itemView.findViewById(R.id.ques);
            ans=itemView.findViewById(R.id.ans);
            cmnt=itemView.findViewById(R.id.comment);
            verse_no=itemView.findViewById(R.id.verse_no);
            sura_no=itemView.findViewById(R.id.sura_no);
            sura_name=itemView.findViewById(R.id.sura_name);

        }
    }

/*

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v=convertView;
        database=new Database(context);

        ansList=database.getAllAns();
        cmntList=database.getAllVerseFaq();

        if (v ==null){
            v=LayoutInflater.from(context).inflate(R.layout.listeview_item,null);

            ques=v.findViewById(R.id.ques);
            ans=v.findViewById(R.id.ans);
            cmnt=v.findViewById(R.id.comment);
        }
        try {
            Log.e("queslist",""+queslist.size());
            ques.setText(queslist.get(position));
        }catch (IndexOutOfBoundsException i){
            i.printStackTrace();
        }
        ans.setText(cmntList.get(position));
        cmnt.setText(ansList.get(position));

        return v;
    }
*/

    public void filterAdapter(ArrayList<String> newList){
        queslist=new ArrayList<>();
        queslist.addAll(newList);
        Log.e("queslist",""+queslist.size());
        notifyDataSetChanged();
    }

    /*public class MyTask extends AsyncTask<Integer,Integer,Boolean> {

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {

                Log.e("Verse No:",""+cmntList.get(integers[0]));
                versNo=database.getVerseNo(cmntList.get(integers[0]));
                suraNo=database.getSuraNo(cmntList.get(integers[0]));


            return true;
        }


        @Override
        protected void onPreExecute() {

            progressDialog.setTitle("Getting Data");
            progressDialog.show();
        }
    }*/
}
