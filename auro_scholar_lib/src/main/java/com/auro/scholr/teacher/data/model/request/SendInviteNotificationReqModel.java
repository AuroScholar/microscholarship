package com.auro.scholr.teacher.data.model.request;

public class SendInviteNotificationReqModel {

    String sender_mobile_no;
    String receiver_mobile_no;
    String notification_title;
    String notification_message;

    public String getSender_mobile_no() {
        return sender_mobile_no;
    }

    public void setSender_mobile_no(String sender_mobile_no) {
        this.sender_mobile_no = sender_mobile_no;
    }

    public String getReceiver_mobile_no() {
        return receiver_mobile_no;
    }

    public void setReceiver_mobile_no(String receiver_mobile_no) {
        this.receiver_mobile_no = receiver_mobile_no;
    }

    public String getNotification_title() {
        return notification_title;
    }

    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }

    public String getNotification_message() {
        return notification_message;
    }

    public void setNotification_message(String notification_message) {
        this.notification_message = notification_message;
    }
}
