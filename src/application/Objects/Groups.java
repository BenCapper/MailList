package application.Objects;


import java.util.*;

public class Groups {
    private String name;
    private HashSet<User> members;
    private HashSet<Message> messages;


    public Groups(String name){
        this.name = name;
        this.members = new HashSet<>();
        this.messages = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<User> getMembers() {
        return this.members;
    }

    public void setMembers(HashSet<User> members) {
        this.members = members;
    }

    public HashSet<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(HashSet<Message> messages) {
        this.messages = messages;
    }



    @Override
    public String toString() {
        return "Groups{" +
                "name='" + name + '\'' +
                ", members=" + members +
                ", messages=" + messages +
                '}';
    }
}
