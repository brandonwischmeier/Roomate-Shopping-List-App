package edu.cs.uga.roommatesshopping.pojo;

/**
 * POJO representing a single message
 */
public class Message {

    private String userId;
    private String text;
    private String name;

    public Message() {
    }

    public Message(String userId, String text, String name) {
        this.userId = userId;
        this.text = text;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
