package application.Objects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Message {
    private String sender, title, priority, body, group;
    private LocalDate date;
    private LocalTime time;


    public Message(String sender, String title, String priority, String body, String group){
        this.sender=sender;
        this.title=title;
        this.priority=priority;
        this.body=body;
        this.time = LocalTime.now();
        this.date = LocalDate.now();
        this.group = group;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", title='" + title + '\'' +
                ", priority='" + priority + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
