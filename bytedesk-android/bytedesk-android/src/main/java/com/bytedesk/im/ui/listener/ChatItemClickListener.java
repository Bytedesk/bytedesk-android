package com.bytedesk.im.ui.listener;

/**
 * @author bytedesk.com on 2017/8/28.
 */

public interface ChatItemClickListener {

//    void onMessageTextItemClick(int position);

    void onMessageImageItemClick(String imageUrl);

    void onMessageVideoItemClick(String videoUrl);

//    void onMessageVoiceItemClick(int position);

}
