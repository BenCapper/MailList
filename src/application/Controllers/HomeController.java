package application.Controllers;

import application.MailingListModel;
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
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private MailingListModel users, messages, groups;
    String types;
    @FXML
    private TextField userRegister, email, userLogin;
    @FXML
    private TextArea feedback;
    @FXML
    private ComboBox<String> accType;
    @FXML
    private PasswordField registerPass, loginPass;

    public HomeController(){}


    public void handleRegisterButton() throws Exception {
        if(userRegister.getText().isEmpty()){
            feedback.appendText("Enter a Username \n");
        }
        else if (users.compareUsername(userRegister.getText())){
            feedback.appendText("Username taken \n");
        }
        else if(email.getText().isEmpty()){
            feedback.appendText("Enter an Email Address \n");
        }
        else if(users.compareEmail(email.getText())){
            feedback.appendText("This Email Address is already registered \n");
        }
        else if(registerPass.getText().isEmpty()){
            feedback.appendText("Enter a Password \n");
        }
        else if(accType.getValue().isEmpty()){
            feedback.appendText("Select Account Type \n");
        }
        else {
            users.addUser(userRegister.getText(),registerPass.getText(), email.getText(), accType.getValue());
            feedback.appendText(userRegister.getText() + " successfully registered \n");
            users.saveUsers();
            users.loadUsers();
        }
    }

    public void handleLoginButton(ActionEvent event) throws IOException {
        if (users.compareUsername(userLogin.getText()) && users.comparePassword(loginPass.getText())) {
            if (users.getUserByName(userLogin.getText()).getAccType().matches("Admin")) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../Fxml/Admin.fxml"));
                Parent adminParent = loader.load();
                Scene adminScene = new Scene(adminParent);

                AdminController controller = loader.getController();
                String loadedUser = userLogin.getText();
                controller.initData(loadedUser);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Admin");
                window.setScene(adminScene);
                window.show();
            } else if (users.getUserByName(userLogin.getText()).getAccType().matches("Member")) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../Fxml/User.fxml"));
                Parent userParent = loader.load();
                Scene userScene = new Scene(userParent);

                UserController controller = loader.getController();
                String loadedUser = userLogin.getText();
                controller.initData(loadedUser);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Member");
                window.setScene(userScene);
                window.show();
            }
        }
        else {
            feedback.appendText("Incorrect Login Details \n");
        }

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

        ObservableList<String> type = FXCollections.observableArrayList("Member", "Admin");
        types = "Select Account Type";
        accType.setItems(type);
        accType.setValue(types);
    }
}
