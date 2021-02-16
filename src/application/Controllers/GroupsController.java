package application.Controllers;

import application.MailingListModel;
import application.Objects.Groups;
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


import java.net.URL;
import java.util.ResourceBundle;

public class GroupsController implements Initializable {
    private MailingListModel users, messages, groups;
    @FXML private TextArea feedback;
    @FXML private Label user;
    @FXML private TableView<Groups> groupTable;
    @FXML private TableView<Groups> memberTable;
    @FXML private TableColumn<Groups, String> name, name2;

    public GroupsController(){}



    public void handleBackButton(ActionEvent event) throws Exception {
        groups.saveGroups();
        if(users.getUserByName(user.getText()).getAccType().matches("Admin")){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Fxml/Admin.fxml"));
            Parent adminParent = loader.load();
            Scene adminScene = new Scene(adminParent);

            AdminController controller = loader.getController();
            String loadedUser = user.getText();
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
            String loadedUser = user.getText();
            controller.initData(loadedUser);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Member");
            window.setScene(userScene);
            window.show();
        }
    }

    public void handleJoinButton() throws Exception {
        Groups selected = groupTable.getSelectionModel().getSelectedItem();
        String name = user.getText();
        User loggedIn = users.getUserByName(name);

        if (groupTable.getSelectionModel().isEmpty()) {
            feedback.appendText("Highlight a group to join \n");
        }
        if(memberTable.getItems().contains(selected)){
            feedback.appendText("You are already a member of this group \n");
        }
        else {
            selected.getMembers().add(loggedIn);
            feedback.appendText(selected.getName() + " joined \n");
            groups.saveGroups();
            ObservableList<Groups> group2 = FXCollections.observableArrayList(groups.getGroupsWithSpecifiedUser(name));
            memberTable.setItems(group2);

        }


    }

    public void handleLeaveButton() throws Exception {
        Groups selected = memberTable.getSelectionModel().getSelectedItem();
        String name = user.getText();
        User loggedIn = users.getUserByName(name);
        if(memberTable.getSelectionModel().isEmpty()){
            feedback.appendText("Highlight a group to leave");
        }
        else{
            selected.getMembers().remove(loggedIn);
            feedback.appendText(selected.getName() + " removed from your groups \n");
            groups.saveGroups();
            ObservableList<Groups> group2 = FXCollections.observableArrayList(groups.getGroupsWithSpecifiedUser(name));
            memberTable.setItems(group2);
        }
    }

    public void initData(String username){
        user.setText(username);
        ObservableList<Groups> memberOf = FXCollections.observableArrayList(groups.getGroupsWithSpecifiedUser(username));
        name2.setCellValueFactory(new PropertyValueFactory<Groups, String>("name"));
        memberTable.setItems(memberOf);
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


        ObservableList<Groups> group = FXCollections.observableArrayList(groups.getGroups());
        name.setCellValueFactory(new PropertyValueFactory<Groups, String>("name"));
        groupTable.setItems(group);



    }
}