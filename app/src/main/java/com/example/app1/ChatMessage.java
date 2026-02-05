package com.example.app1;

/**
 * Model class representing a chat message.
 */
public class ChatMessage {

    private String message;
    private boolean isSent;
    private long timestamp;

    public ChatMessage(String message, boolean isSent) {
        this.message = message;
        this.isSent = isSent;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
