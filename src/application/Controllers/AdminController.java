package application.Controllers;

import application.Objects.Groups;
import application.MailingListModel;
import application.Objects.Message;
import application.Objects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    private MailingListModel users, groups, messages;
    String types, nameList, priorities;
    @FXML private TextField tab1SearchUser, tab1CreateUser, tab1CreatePass, tab1CreateEmail, tab2SearchGroup, tab2CreateName, tab3MessageTitle;
    @FXML private Label loggedIn;
    @FXML private TextArea tab1Feedback, tab2Feedback, tab3Feedback, tab3Message;
    @FXML private Button tab1Search, back, updateUser, createUser, tab2Search, createGroup, updateGroup, deleteGroup, removeUser, tab3Search, editMessage, deleteMessage, sendMessage;
    @FXML private ComboBox <String> accountType;
    @FXML private ComboBox <String> messageRecipient;
    @FXML private ComboBox <String> prio;
    @FXML private TableView<User> userTable;
    @FXML private TableView<Groups> groupTable;
    @FXML private TableView<User> membersOfGroupTable;
    @FXML private TableView<Message> messagesTable;
    @FXML private TableColumn<User, String> username;
    @FXML private TableColumn<User, String> email;
    @FXML private TableColumn<User, String> accType;
    @FXML private TableColumn<User, String> usernameGroupPanel;
    @FXML private TableColumn<User, String> emailGroupPanel;
    @FXML private TableColumn<User, String> accTypeGroupPanel;
    @FXML private TableColumn<User, LocalDate> signUpDate, lastPostDate;
    @FXML private TableColumn<Groups, String> name;
    @FXML private TableColumn<Message, String> sender, title, priority, body;
    @FXML private TableColumn<Message, LocalTime> time;
    @FXML private TableColumn<Message, LocalDate> date;

    public AdminController(){}

    public void handleTab1SearchButton(){
        if(users.getUsersLength() <= 0){
            tab1Feedback.appendText("There are no Users registered \n");
        }
        else if(!users.compareUsername(tab1SearchUser.getText())){
            tab1Feedback.appendText("There are no Users with that name \n");
        }
        else {
            String userInput = tab1SearchUser.getText();
            if(users.compareUsername(userInput)){
                ObservableList<User> userList = FXCollections.observableArrayList(users.getUserByName(userInput));
                userTable.setItems(userList);
            }
        }
    }
    public void handleBackButton(ActionEvent event) throws IOException{
        Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../Fxml/Home.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        primaryStage.setTitle("WoW Messages - Login");
        primaryStage.setScene(new Scene(root, 750, 500));
        primaryStage.show();

    }
    public void handleResetButton(){
        ObservableList<User> userList = FXCollections.observableArrayList(users.getUsers());
        userTable.setItems(userList);
    }
    public void handleSelectButton(){
        User selected = userTable.getSelectionModel().getSelectedItem();
        tab1CreateUser.setText(selected.getUsername());
        tab1CreatePass.setText(selected.getPassword());
        tab1CreateEmail.setText(selected.getEmail());
        accountType.setValue(selected.getAccType());
    }
    public void handleUpdateUserButton() throws Exception {
        if(users.compareUsername(tab1CreateUser.getText())) {
            users.updateUser(tab1CreateUser.getText(), tab1CreatePass.getText(), tab1CreateEmail.getText(), accountType.getValue());
            users.saveUsers();
            users.loadUsers();
            ObservableList<User> userList = FXCollections.observableArrayList(users.getUsers());
            userTable.setItems(userList);
            tab1Feedback.appendText(tab1CreateUser.getText() + " Updated \n");
        }
        else{
            tab1Feedback.appendText("Enter a Username whose details should be updated \n");
        }
    }
    public void handleCreateUserButton() throws Exception {
        if(tab1CreateUser.getText().isEmpty()){
            tab1Feedback.setText("Enter a Valid Username \n");
        }
        else if(users.compareUsername(tab1CreateUser.getText())){
            tab1Feedback.appendText("This Username is already taken \n");
        }
        else if(tab1CreatePass.getText().isEmpty()){
            tab1Feedback.appendText("Enter a Password \n");
        }
        else if(tab1CreateEmail.getText().isEmpty()){
            tab1Feedback.appendText("Enter an Email Address \n");
        }
        else if(users.compareEmail(tab1CreateEmail.getText())){
            tab1Feedback.appendText("This Email Address is already registered \n");
        }
        else if(accountType.getValue().matches("Select Account Type")){
            tab1Feedback.appendText("Select an Account Type \n");
        }
        else if(accountType.getValue() == null){
            tab1Feedback.appendText("Select an Account Type \n");
        }
        else{
            users.addUser(tab1CreateUser.getText(), tab1CreatePass.getText(), tab1CreateEmail.getText(), accountType.getValue());
            tab1Feedback.setText(tab1CreateUser.getText() + " successfully registered. \n");
            users.saveUsers();
            ObservableList<User> userList = FXCollections.observableArrayList(users.getUsers());
            userTable.setItems(userList);
        }
    }
    public void handleDeleteUserButton()throws Exception {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if(userTable.getSelectionModel().isEmpty()){
            tab1Feedback.appendText("Highlight a user to delete \n");
        }
        else {
            users.removeUser(selected.getUsername());
            tab1Feedback.appendText(selected.getUsername() + " successfully deleted \n");
            users.saveUsers();
            users.loadUsers();
            ObservableList<User> userList = FXCollections.observableArrayList(users.getUsers());
            userTable.setItems(userList);
        }

    }
    public void handleTab2SearchButton(){
        if(groups.getGroupsLength() <= 0){
            tab2Feedback.appendText("There are no Groups registered \n");
        }
        else if(!groups.compareGroupName(tab2SearchGroup.getText())){
            tab2Feedback.appendText("There are no Groups with that name \n");
        }
        else {
            String userInput = tab1SearchUser.getText();
            if(groups.compareGroupName(userInput)){
                ObservableList<Groups> groupList = FXCollections.observableArrayList(groups.getGroupByName(userInput));
                groupTable.setItems(groupList);
            }
        }
    }
    public void handleCreateGroupButton() throws Exception {
        if(tab2CreateName.getText().isEmpty()){
            tab2Feedback.appendText("Enter a Group Name \n");
        }
        else if (groups.compareGroupName(tab2CreateName.getText())){
            tab2Feedback.appendText("This Group Name is already in use \n");
        }
        else {
            groups.addGroup(tab2CreateName.getText());
            groups.saveGroups();
            groups.loadGroups();
            ObservableList<Groups> groupList = FXCollections.observableArrayList(groups.getGroups());
            groupTable.setItems(groupList);
        }
    }
    public void handleDeleteGroupButton() throws Exception {
        Groups selected = groupTable.getSelectionModel().getSelectedItem();
        if(groupTable.getSelectionModel().isEmpty()){
            tab2Feedback.appendText("Highlight a group to delete \n");
        }
        else {
            groups.removeGroup(selected.getName());
            tab2Feedback.appendText(selected.getName() + " successfully deleted \n");
            groups.saveGroups();
            groups.loadGroups();
            ObservableList<Groups> groupList = FXCollections.observableArrayList(groups.getGroups());
            groupTable.setItems(groupList);
        }
    }
    public void handleViewMembersButton(){
        if(groupTable.getSelectionModel().isEmpty()){
            tab2Feedback.appendText("Highlight a group to view it's members \n");
        }
        else {
            tab2CreateName.setText(groupTable.getSelectionModel().getSelectedItem().getName());
            ObservableList<User> userList = FXCollections.observableArrayList(groupTable.getSelectionModel().getSelectedItem().getMembers());
            membersOfGroupTable.setItems(userList);
        }
    }
    public void handleRemoveUserButton() throws Exception {
        Groups group = groupTable.getSelectionModel().getSelectedItem();
        User user = membersOfGroupTable.getSelectionModel().getSelectedItem();
        if(groups.getMembersOfGroup(group.getName()).contains(user)){
            group.getMembers().remove(user);
            users.saveUsers();
            users.loadUsers();
            groups.getGroupByName(group.getName()).getMembers().remove(user);
            groups.saveGroups();
            ObservableList<User> userList = FXCollections.observableArrayList(groupTable.getSelectionModel().getSelectedItem().getMembers());
            membersOfGroupTable.setItems(userList);
            tab2Feedback.appendText("User removed from group \n");
        }
    }

    public void handleDeleteMessageButton() throws Exception{
        Message selected = messagesTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            tab3Feedback.appendText("Highlight the message to be deleted \n");
        }
        else{
            messages.removeMessage(selected.getBody());
            tab3Feedback.appendText("Message deleted \n");
            messages.saveMessages();
            messages.loadMessages();
            ObservableList<Message> messageList = FXCollections.observableArrayList(messages.getMessages());
            messagesTable.setItems(messageList);
        }
    }

    public void handleYourGroupsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Fxml/GroupDisplay.fxml"));
        Parent groupParent = loader.load();
        Scene groupScene = new Scene(groupParent);

        GroupsController controller = loader.getController();
        String loadedUser = loggedIn.getText();
        controller.initData(loadedUser);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Group");
        window.setScene(groupScene);
        window.show();
    }

    public void handleViewMessageButton(ActionEvent event) throws IOException {
        if(messagesTable.getSelectionModel().isEmpty()){
            tab3Feedback.appendText("Highlight a message to view");
        }
        else {
            Message messageSelected = (Message) messagesTable.getSelectionModel().getSelectedItem();
            tab3Message.setText(messageSelected.getBody());
            tab3MessageTitle.setText(messageSelected.getTitle());
            messageRecipient.setValue(messageSelected.getGroup());
            prio.setValue(messageSelected.getPriority());
        }
    }
    public void handleSendMessageButton() throws Exception {
        if(tab3Message.getText().isEmpty()){
            tab3Feedback.appendText("Enter Message Body \n");
        }
        else if(tab3MessageTitle.getText().isEmpty()){
            tab3Feedback.appendText("Enter Message Title \n");
        }
        else if(tab3MessageTitle.getText().isEmpty()){
            tab3Feedback.appendText("Enter Message Title \n");
        }
        else if(messageRecipient.getValue().matches("Select Recipient") || messageRecipient.getValue().isEmpty()){
            tab3Feedback.appendText("Select Message Recipient \n");
        }
        else{
            messages.addMessage(loggedIn.getText(),tab3MessageTitle.getText(), prio.getValue(), tab3Message.getText(), messageRecipient.getValue());
            groups.getGroupByName(messageRecipient.getValue()).getMessages().add(messages.getMessageByBody(tab3Message.getText()));
            groups.saveGroups();
            messages.saveMessages();
            messages.loadMessages();
            ObservableList<Message> messageList = FXCollections.observableArrayList(messages.getMessages());
            messagesTable.setItems(messageList);
            tab3Feedback.appendText("Message sent to " + messageRecipient.getValue() + "\n");

        }
    }

    public void initData(String username){
        loggedIn.setText(username);
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        users = new MailingListModel();
        groups = new MailingListModel();
        messages = new MailingListModel();


        ObservableList<String> type = FXCollections.observableArrayList("Member", "Admin");
        types = "Member";
        accountType.setItems(type);
        accountType.setValue(types);

        ObservableList<String> prioList = FXCollections.observableArrayList("Low", "Normal", "Urgent");
        priorities = "Priority";
        prio.setItems(prioList);
        prio.setValue(priorities);

        try {
            users.loadUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            groups.loadGroups();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            messages.loadMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }




        ObservableList<String> recipients = FXCollections.observableArrayList(groups.getAllGroupNames());
        nameList = "Select Recipient";
        messageRecipient.setItems(recipients);
        messageRecipient.setValue(nameList);

        ObservableList<User> usersList = FXCollections.observableArrayList(users.getUsers());
        username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        accType.setCellValueFactory(new PropertyValueFactory<User, String>("accType"));
        signUpDate.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("signUpDate"));
        lastPostDate.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("lastPostDate"));
        userTable.setItems(usersList);

        ObservableList<Groups> group = FXCollections.observableArrayList(groups.getGroups());
        name.setCellValueFactory(new PropertyValueFactory<Groups, String>("name"));
        groupTable.setItems(group);

        ObservableList<Message> message = FXCollections.observableArrayList(messages.getMessages());
        sender.setCellValueFactory(new PropertyValueFactory<Message, String>("sender"));
        title.setCellValueFactory(new PropertyValueFactory<Message, String>("title"));
        priority.setCellValueFactory(new PropertyValueFactory<Message, String>("priority"));
        time.setCellValueFactory(new PropertyValueFactory<Message, LocalTime>("time"));
        date.setCellValueFactory(new PropertyValueFactory<Message, LocalDate>("date"));
        body.setCellValueFactory(new PropertyValueFactory<Message, String>("body"));
        messagesTable.setItems(message);

        ObservableList<User> membersOfGroup = FXCollections.observableArrayList((User) null);
        usernameGroupPanel.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        emailGroupPanel.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        accTypeGroupPanel.setCellValueFactory(new PropertyValueFactory<User, String>("accType"));
        membersOfGroupTable.setItems(membersOfGroup);
    }
}
