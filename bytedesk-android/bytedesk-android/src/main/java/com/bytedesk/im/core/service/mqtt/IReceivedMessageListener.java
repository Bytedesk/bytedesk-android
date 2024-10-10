package com.bytedesk.im.core.service.mqtt;

import com.bytedesk.im.core.service.mqtt.model.ReceivedMessage;

public interface IReceivedMessageListener {

    void onMessageReceived(ReceivedMessage message);
}