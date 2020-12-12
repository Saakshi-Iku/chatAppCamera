package com.example.chatappss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class Chat_Image_Adapter extends RecyclerView.Adapter<Chat_Image_Adapter.Example_View_Holder> {
    private Context mContext;
    public ArrayList<Ex_image_selected_java> ex_list;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }
    public static class Example_View_Holder extends RecyclerView.ViewHolder {

        public ImageView iv;

        public Example_View_Holder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }

                }
            });

        }
    }

    public Chat_Image_Adapter(ArrayList<Ex_image_selected_java> ex_list, Context context)
    {
        this.ex_list=ex_list;
        this.mContext = context;
    }
    @NonNull
    @Override
    public Example_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.ex_image_selected_layout,parent,false);
        Example_View_Holder evh=new Example_View_Holder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull Example_View_Holder holder, int position) {
        Ex_image_selected_java currentItem=ex_list.get(position);
        //Picasso.with(mContext).load(currentItem.getimage()).into(holder.iv);
        File imgFile = new  File(currentItem.getimage());

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            holder.iv.setImageBitmap(myBitmap);

        }
    }

    @Override
    public int getItemCount() {
        return ex_list.size();
    }


}