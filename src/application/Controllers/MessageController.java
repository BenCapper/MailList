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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MessageController implements Initializable {
    private MailingListModel users, messages, groups;
    private String priorities;
    @FXML private TextArea message, reply, feedback;
    @FXML private TextField title;
    @FXML private Button back, clear, send;
    @FXML private Label sentBy, dateReceived, groupLabel, userLabel;
    @FXML private ComboBox<String> prio;

    public MessageController(){}

    public void initData(Message loadedMessage, String username){
        message.setText(loadedMessage.getBody());
        sentBy.setText(loadedMessage.getSender());
        dateReceived.setText(loadedMessage.getDate().toString());
        groupLabel.setText(loadedMessage.getGroup());
        userLabel.setText(username);
        title.setText(loadedMessage.getTitle());
    }

    public void handleBackButton(ActionEvent event) throws IOException {
        if(users.getUserByName(userLabel.getText()).getAccType().matches("Admin")){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Fxml/Admin.fxml"));
            Parent adminParent = loader.load();
            Scene adminScene = new Scene(adminParent);

            AdminController controller = loader.getController();
            String loadedUser = userLabel.getText();
            controller.initData(loadedUser);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Admin");
            window.setScene(adminScene);
            window.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Fxml/User.fxml"));
            Parent userParent = loader.load();
            Scene userScene = new Scene(userParent);

            UserController controller = loader.getController();
            String loadedUser = userLabel.getText();
            controller.initData(loadedUser);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Member");
            window.setScene(userScene);
            window.show();
        }
    }


    public void handleClearButton(){
        reply.clear();
        title.clear();
    }

    public void handleSendButton() throws Exception {
        if(title.getText().isEmpty()){
            feedback.appendText("Enter a Message Title \n");
        }
        else if(message.getText().isEmpty()){
            feedback.appendText("Enter a Message \n");
        }
        else if(groupLabel.getText().matches("Select Group") || groupLabel.getText().isEmpty()){
            feedback.appendText("Select the Recipient Group \n");
        }
        else if(prio.getValue().matches("Select Priority") || prio.getValue().isEmpty()){
            feedback.appendText("Select the Message Priority \n");
        }
        else{
            Message newMessage = new Message(userLabel.getText(), title.getText(), prio.getValue(), message.getText(), groupLabel.getText());
            messages.addMessage(userLabel.getText(), title.getText(), prio.getValue(), message.getText(), groupLabel.getText());
            groups.getGroupByName(groupLabel.getText()).getMessages().add(newMessage);
            feedback.appendText("Message Sent to " + groupLabel.getText());
            users.getUserByName(userLabel.getText()).setLastPostDate(LocalDate.now());
        }
        messages.saveMessages();
        groups.saveGroups();
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

        ObservableList<String> prioList = FXCollections.observableArrayList("Low", "Normal", "Urgent");
        priorities = "Priority";
        prio.setItems(prioList);
        prio.setValue(priorities);
    }
}
