package com.bytedesk.ui.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bytedesk.ui.util.BDUiUtils;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.bytedesk.core.room.entity.MessageEntity;
import com.bytedesk.ui.R;
import com.bytedesk.ui.listener.ChatItemClickListener;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.textview.QMUILinkTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ningjinpeng on 2017/8/23.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private List<MessageEntity> mMessages;
    private ChatItemClickListener mChatItemClickListener;

    public ChatAdapter(Context context, ChatItemClickListener chatItemClickListener) {
        mContext = context;
        mMessages = new ArrayList<>();
        mChatItemClickListener = chatItemClickListener;
    }

    public void setMessages(final List<MessageEntity> messageEntities) {
        if (null == mMessages) {
            mMessages = messageEntities;
            notifyItemRangeInserted(0, messageEntities.size());
        }
        else {
            mMessages = messageEntities;
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;

        switch (viewType) {
            case MessageEntity.TYPE_TEXT_ID:
                layout = R.layout.wx_message_item_text;
                break;
            case MessageEntity.TYPE_TEXT_SELF_ID:
                layout = R.layout.wx_message_item_text_self;
                break;
            case MessageEntity.TYPE_IMAGE_ID:
                layout = R.layout.wx_message_item_image;
                break;
            case MessageEntity.TYPE_IMAGE_SELF_ID:
                layout = R.layout.wx_message_item_image_self;
                break;
            case MessageEntity.TYPE_VOICE_ID:
                layout = R.layout.wx_message_item_voice;
                break;
            case MessageEntity.TYPE_VOICE_SELF_ID:
                layout = R.layout.wx_message_item_voice_self;
                break;
            case MessageEntity.TYPE_NOTIFICATION_ID:
                layout = R.layout.wx_message_item_notification;
                break;
            case MessageEntity.TYPE_QUEUE_NOTIFICATION_ID:
                layout = R.layout.wx_message_item_notification;
                break;
            default:
                layout = R.layout.wx_message_item_text;
        }

        View view = LayoutInflater.from(mContext).inflate(layout, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        MessageEntity message = mMessages.get(position);
        viewHolder.setContent(message);

        if (null != mChatItemClickListener) {
            viewHolder.setItemClickListener(this.mChatItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mMessages == null ? 0 : mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages == null ? MessageEntity.TYPE_TEXT_ID : mMessages.get(position).getTypeId();
    }

    //
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private int messageViewType;
        private TextView timestampTextView;
        private TextView nicknameTextView;
        private QMUIRadiusImageView avatarImageView;
        // 文字消息
        private QMUILinkTextView contentTextView;
        // 图片消息
        private ImageView imageImageView;
        // 通知消息
        private TextView notificationTextView;

        private ChatItemClickListener itemClickListener;

        public ViewHolder(View itemView, int msgeViewType) {
            super(itemView);
            //
            messageViewType = msgeViewType;
            timestampTextView = itemView.findViewById(R.id.kfds_message_item_timestamp_textview);

            // 文字消息
            if (messageViewType == MessageEntity.TYPE_TEXT_ID
                    || messageViewType == MessageEntity.TYPE_TEXT_SELF_ID
                    || messageViewType == MessageEntity.TYPE_EVENT_ID
                    || messageViewType == MessageEntity.TYPE_EVENT_SELF_ID) {
                avatarImageView = itemView.findViewById(R.id.kfds_message_item_header);
                avatarImageView.setBorderColor(ContextCompat.getColor(mContext, R.color.wx_config_color_gray_6));
                avatarImageView.setBorderWidth(QMUIDisplayHelper.dp2px(mContext, 1));
                avatarImageView.setSelectedMaskColor(ContextCompat.getColor(mContext, R.color.wx_config_color_gray_8));
                avatarImageView.setSelectedBorderColor(ContextCompat.getColor(mContext, R.color.wx_config_color_gray_4));
                avatarImageView.setTouchSelectModeEnabled(true);
                avatarImageView.setCircle(true);
                contentTextView = itemView.findViewById(R.id.kfds_message_item_content);
            }
            // 图片消息
            else if (messageViewType == MessageEntity.TYPE_IMAGE_ID
                    || messageViewType == MessageEntity.TYPE_IMAGE_SELF_ID) {
                avatarImageView = itemView.findViewById(R.id.kfds_message_item_header);
                avatarImageView.setBorderColor(ContextCompat.getColor(mContext, R.color.wx_config_color_gray_6));
                avatarImageView.setBorderWidth(QMUIDisplayHelper.dp2px(mContext, 1));
                avatarImageView.setSelectedMaskColor(ContextCompat.getColor(mContext, R.color.wx_config_color_gray_8));
                avatarImageView.setSelectedBorderColor(ContextCompat.getColor(mContext, R.color.wx_config_color_gray_4));
                avatarImageView.setTouchSelectModeEnabled(true);
                avatarImageView.setCircle(true);
                imageImageView = itemView.findViewById(R.id.kfds_message_item_image);
            }
            // 通知消息
            else if (messageViewType == MessageEntity.TYPE_NOTIFICATION_ID
                    || messageViewType == MessageEntity.TYPE_QUEUE_NOTIFICATION_ID) {
                notificationTextView = itemView.findViewById(R.id.kfds_message_item_notification_textview);
            }

            // 收到的消息
            if (messageViewType == MessageEntity.TYPE_TEXT_ID
                    || messageViewType == MessageEntity.TYPE_IMAGE_ID
                    || messageViewType == MessageEntity.TYPE_VOICE_ID) {
                nicknameTextView = itemView.findViewById(R.id.kfds_message_item_nickname);
            }
        }

        public void setContent(final MessageEntity msgEntity) {
//            Logger.d(msgEntity.getAvatar());

            timestampTextView.setText(BDUiUtils.friendlyTime(msgEntity.getCreatedAt(), mContext));

            // 文字消息
            if (messageViewType == MessageEntity.TYPE_TEXT_ID
                    || messageViewType == MessageEntity.TYPE_TEXT_SELF_ID
                    || messageViewType == MessageEntity.TYPE_EVENT_ID
                    || messageViewType == MessageEntity.TYPE_EVENT_SELF_ID) {
                //
                Glide.with(mContext).load(msgEntity.getAvatar()).into(avatarImageView);
                avatarImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Logger.d("avatar clicked:" + msgEntity.getAvatar());
                    }
                });


                //
                contentTextView.setText(msgEntity.getContent());
                contentTextView.setOnLinkClickListener(new QMUILinkTextView.OnLinkClickListener() {

                    @Override
                    public void onTelLinkClick(String phoneNumber) {
                        Toast.makeText(mContext, "识别到电话号码是：" + phoneNumber, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMailLinkClick(String mailAddress) {
                        Toast.makeText(mContext, "识别到邮件地址是：" + mailAddress, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWebUrlLinkClick(String url) {
                        Toast.makeText(mContext, "识别到网页链接是：" + url, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            // 图片消息
            else if (messageViewType == MessageEntity.TYPE_IMAGE_ID
                    || messageViewType == MessageEntity.TYPE_IMAGE_SELF_ID) {
                //
                Glide.with(mContext).load(msgEntity.getAvatar()).into(avatarImageView);
                avatarImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Logger.d("avatar clicked:" + msgEntity.getAvatar());
                    }
                });
                //
                Glide.with(mContext).load(msgEntity.getImageUrl()).into(imageImageView);
                imageImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Logger.d("image clicked:" + msgEntity.getImageUrl());
                        if (null != itemClickListener)
                            itemClickListener.onMessageImageItemClick(msgEntity.getImageUrl());
                    }
                });
            }
            // 通知消息
            else if (messageViewType == MessageEntity.TYPE_NOTIFICATION_ID
                    || messageViewType == MessageEntity.TYPE_QUEUE_NOTIFICATION_ID) {
                notificationTextView.setText(msgEntity.getContent());
            }


            // 收到的消息
            if (messageViewType == MessageEntity.TYPE_TEXT_ID
                    || messageViewType == MessageEntity.TYPE_IMAGE_ID
                    || messageViewType == MessageEntity.TYPE_VOICE_ID) {
                nicknameTextView.setText(msgEntity.getNickname());
            }

        }

        @Override
        public void onClick(View view) {
            if (null != itemClickListener) {
            }
        }

        public void setItemClickListener(ChatItemClickListener chatItemClickListener) {
            this.itemClickListener = chatItemClickListener;
        }

    }

}
