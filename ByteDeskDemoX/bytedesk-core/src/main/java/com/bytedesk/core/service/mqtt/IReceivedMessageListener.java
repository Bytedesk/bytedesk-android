package com.bytedesk.core.service.mqtt;

import com.bytedesk.core.service.mqtt.model.ReceivedMessage;

public interface IReceivedMessageListener {

    void onMessageReceived(ReceivedMessage message);
}