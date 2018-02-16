package com.ngobrolyuk.android.adapters;

/**
 * Created by MIP on 1/6/2018.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ngobrolyuk.android.R;
import com.ngobrolyuk.android.activities.ChatActivity;
import com.ngobrolyuk.android.models.ChatRoom;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hendiware on 2016/12 .
 */

public class ChatRoomsAdapter extends
        RecyclerView.Adapter<ChatRoomsAdapter.ChatRoomHolder> {

    // define list and context
    private List<ChatRoom> chatRooms;
    private Context context;

    // constructor
    public ChatRoomsAdapter(List<ChatRoom> chatRooms, Context context) {

        this.chatRooms = chatRooms;
        this.context = context;
    }

    @Override
    public ChatRoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create view holder
        return new ChatRoomHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat_room, parent, false));
    }

    @Override
    public void onBindViewHolder(ChatRoomHolder holder, int position) {
        // get room
        final ChatRoom room = chatRooms.get(position);
        // set room data for row
        holder.tvTitle.setText(room.room_name);
        holder.tvDesc.setText(room.room_desc);
        // start chat activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("room_id", Integer.parseInt(room.id));
                intent.putExtra("room_name", room.room_name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }

    class ChatRoomHolder extends RecyclerView.ViewHolder {
        // declare views using butter knife
        @BindView(R.id.img_group_icon)
        ImageView imgGroupIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.rllt_body)
        RelativeLayout rlltBody;

        public ChatRoomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
