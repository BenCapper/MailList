package application;

import application.Objects.Groups;
import application.Objects.Message;
import application.Objects.User;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;


public class MailingListModel {

    private HashMap<String, User> users;
    private HashMap<String, Groups> groups;
    private HashMap<String, Message> messages;

    public MailingListModel() {

        users = new HashMap<>();
        groups = new HashMap<>();
        messages = new HashMap<>();
    }

    public  HashSet<User> getUsers(){
        HashSet map = new HashSet<User>();
        for(User user : (users.values())){
            map.add(user);
        }
        return map;
    }

    public User getUserByName(String username){
        if(users.containsKey(username)){
            User user = users.get(username);
            return user;
        }
        else{
            return null;
        }
    }

    public HashSet<String> getAllUsernames() {
        HashSet map = new HashSet<String>();
        if (users.size() > 0) {
            for (User user : users.values()) {
                map.add(user.getUsername());
            }
        }
        return map;
    }

    public HashSet<String> getAllGroupNames() {
        HashSet map = new HashSet<String>();
        if (groups.size() > 0) {
            for (Groups group : groups.values()) {
                map.add(group.getName());
            }
        }
        return map;
    }

    public Groups getGroupByName(String name){
        if(groups.containsKey(name)){
            Groups group = groups.get(name);
            return group;
        }
        else{
            return null;
        }
    }

    public Message getMessageByBody(String body){
        if(messages.containsKey(body)){
            Message message = messages.get(body);
            return message;
        }
        else{
            return null;
        }
    }

    public void removeUser(String name){
        if(users.containsKey(name)){
            users.remove(name);
        }
    }

    public void removeGroup(String name){
        if(groups.containsKey(name)){
            groups.remove(name);
        }
    }

    public void removeMessage(String body){
        if(messages.containsKey(body)){
            messages.remove(body);
        }
    }

    public boolean compareUsername(String username){
        for(User user : users.values()){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean compareGroupName(String name){
        for(Groups group : groups.values()){
            if(group.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public boolean compareEmail(String email){
        for(User user : users.values()){
            if(user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public boolean comparePassword(String password){
        for(User user : users.values()){
            if(user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public  HashSet<Groups> getGroups(){
        HashSet map = new HashSet<Groups>();
        for(Groups group : (groups.values())){
            map.add(group);
        }
        return map;
    }

    public  HashSet<Groups> getGroupsWithSpecUser(String username){
        HashSet map = new HashSet<Groups>();
        for (Groups group: groups.values()) {
                if (!groups.get(group.getName()).getMembers().contains(users.get(username))) {
                    map.add(groups.get(group.getName()));
                }
        }
        return map;
    }

    public  HashSet<Groups> getGroupsWithSpecifiedUser(String username){
        HashSet map = new HashSet<Groups>();
        for(Groups group : (groups.values())) {
            if (!group.getMembers().isEmpty()) {
                if (group.getMembers().iterator().next().getUsername().matches(username)) {
                    map.add(group);
                }
            }
        }
        return map;
    }


    public  HashSet<User> getMembersOfGroup(String groupName) {
        HashSet map = new HashSet<User>();
        for(Groups group : groups.values()){
            if(group.getName().equals(groupName)){
                map.addAll(group.getMembers());
            }
        }
        return map;
    }

    public HashSet<Message> getMembersMessages(String username){
        HashSet map = new HashSet<Message>();
        for(Message message : messages.values()) {
            if (message.getSender().equals(username)) {
                map.add(message);
            }
        }
        return map;
    }




    public  HashSet<Message> getMessagesOfGroup(String groupName) {
        HashSet map = new HashSet<Message>();
        for(Message message : messages.values()){
            if(message.getGroup().equals(groupName)){
                map.add(message);
            }
        }
        return map;
    }

    public  HashSet<Message> getMessages(){
        HashSet map = new HashSet<Message>();
        for(Message message : (messages.values())){
            map.add(message);
        }
        return map;
    }


    public int getUsersLength(){
        return users.keySet().size();
    }

    public int getGroupsLength(){
        return groups.keySet().size();
    }

    public int getMessagesLength(){
        return groups.keySet().size();
    }

    public void addUser(String username, String password, String email, String accType){
        User user = new User(username,password,email,accType);
        users.put(username,user);
    }

    public void addUserToGroup(String username, String name){
        User newUser = new User("ben2", "ben","ben","admin");
        Groups newGroup = null;

        for(Groups group : groups.values()){
            if(group.getName().equals(name)){
                newGroup = group;
                System.out.println("Group made");
            }
        }
        for(User userObject : users.values()){
            if(userObject.getUsername().equalsIgnoreCase(username)){
                newUser = userObject;
                System.out.println("user made");
            }
        }
        if(newUser.getUsername().equals(username) && newGroup.getName().equals(name)){
            newGroup.getMembers().add(newUser);
            System.out.println("user added");
            System.out.println(groups);

        }
    }

    public void addGroup(String name){
        Groups group = new Groups(name);
        groups.put(name,group);
    }

    public void addMessage(String sender, String title, String priority, String body, String group){
        Message message = new Message(sender, title, priority, body, group);
        messages.put(body,message);
    }

    public void updateUser(String username, String password, String email, String accType) {
        if(users.containsKey(username)){
            users.get(username).setPassword(password);
            users.get(username).setEmail(email);
            users.get(username).setAccType(accType);
        }
    }

    public void saveUsers() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream
                (new FileWriter("users.xml"));
        out.writeObject(users);
        out.close();
    }

    public void loadUsers() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectInputStream is = xstream.createObjectInputStream
                (new FileReader("users.xml"));
        users = (HashMap<String,User>) is.readObject();
        is.close();
    }

    public void saveGroups() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream
                (new FileWriter("groups.xml"));
        out.writeObject(groups);
        out.close();
    }

    public void loadGroups() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectInputStream is = xstream.createObjectInputStream
                (new FileReader("groups.xml"));
        groups = (HashMap<String,Groups>) is.readObject();
        is.close();
    }

    public void saveMessages() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream
                (new FileWriter("messages.xml"));
        out.writeObject(messages);
        out.close();
    }

    public void loadMessages() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectInputStream is = xstream.createObjectInputStream
                (new FileReader("messages.xml"));
        messages = (HashMap<String,Message>) is.readObject();
        is.close();
    }

}
