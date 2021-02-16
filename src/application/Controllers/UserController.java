package application.Controllers;

import application.MailingListModel;
import application.Objects.Message;
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

public class UserController implements Initializable {
    private MailingListModel users, groups, messages;
    String prio, groupNames;
    @FXML private TextField messageTitle;
    @FXML private Label user;
    @FXML private TextArea message, feedback;
    @FXML private Button groupList, search, clear, send, back;
    @FXML private ComboBox<String> recipientGroup;
    @FXML private ComboBox<String> priority;
    @FXML private TableView messageTable;
    @FXML private TableColumn<Message, String> userCol;
    @FXML private TableColumn<Message, String> pri;
    @FXML private TableColumn<Message, String> title;
    @FXML private TableColumn<Message, String> body;
    @FXML private TableColumn<Message, LocalDate> date;
    @FXML private TableColumn<Message, LocalTime> time;


    public UserController(){}

    public void handleGroupButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Fxml/GroupDisplay.fxml"));
        Parent groupParent = loader.load();
        Scene groupScene = new Scene(groupParent);

        GroupsController controller = loader.getController();
        String loadedUser = user.getText();
        controller.initData(loadedUser);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Group");
        window.setScene(groupScene);
        window.show();
    }

    public void handleBackButton(ActionEvent event){
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

    public void handleViewMessageButton(ActionEvent event) throws IOException {
        if(messageTable.getSelectionModel().isEmpty()){
            feedback.appendText("Highlight a message to view");
        }
        else {
            Message messageSelected = (Message) messageTable.getSelectionModel().getSelectedItem();
            message.setText(messageSelected.getBody());
            messageTitle.setText(messageSelected.getTitle());
            recipientGroup.setValue(messageSelected.getGroup());
            priority.setValue(messageSelected.getPriority());
        }
    }

    public void handleClearButton(){
        message.clear();
        messageTitle.clear();
    }

    public void handleSendButton() throws Exception {
        if(messageTitle.getText().isEmpty()){
            feedback.appendText("Enter a Message Title \n");
        }
        else if(message.getText().isEmpty()){
            feedback.appendText("Enter a Message \n");
        }
        else if(recipientGroup.getValue().matches("Select Group") || recipientGroup.getValue().isEmpty()){
            feedback.appendText("Select the Recipient Group \n");
        }
        else if(priority.getValue().matches("Select Priority") || priority.getValue().isEmpty()){
            feedback.appendText("Select the Message Priority \n");
        }
        else{
            Message newMessage = new Message(user.getText(), messageTitle.getText(), priority.getValue(), message.getText(), recipientGroup.getValue());
            messages.addMessage(user.getText(), messageTitle.getText(), priority.getValue(), message.getText(), recipientGroup.getValue());
            groups.getGroupByName(recipientGroup.getValue()).getMessages().add(newMessage);
            messages.saveMessages();
            groups.saveGroups();
            feedback.appendText("Message Sent to " + recipientGroup.getValue() + "\n");
            users.getUserByName(user.getText()).setLastPostDate(LocalDate.now());
            ObservableList<Message> mess = FXCollections.observableArrayList(messages.getMessages());
            messageTable.setItems(mess);
        }
    }

    public void initData(String username){

        user.setText(username);
        ObservableList<Message> message = FXCollections.observableArrayList(messages.getMessages());
        userCol.setCellValueFactory(new PropertyValueFactory<Message, String>("sender"));
        title.setCellValueFactory(new PropertyValueFactory<Message, String>("title"));
        pri.setCellValueFactory(new PropertyValueFactory<Message, String>("priority"));
        time.setCellValueFactory(new PropertyValueFactory<Message, LocalTime>("time"));
        date.setCellValueFactory(new PropertyValueFactory<Message, LocalDate>("date"));
        body.setCellValueFactory(new PropertyValueFactory<Message, String>("body"));
        messageTable.setItems(message);
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        users = new MailingListModel();
        groups = new MailingListModel();
        messages = new MailingListModel();

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

        ObservableList<String> prioChoices = FXCollections.observableArrayList("Low", "Normal", "Urgent");
        prio = "Select Priority";
        priority.setItems(prioChoices);
        priority.setValue(prio);

        ObservableList<String> group = FXCollections.observableArrayList(groups.getAllGroupNames());
        groupNames = "Select Group";
        recipientGroup.setItems(group);
        recipientGroup.setValue(groupNames);



    }
}
