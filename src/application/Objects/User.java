package application.Objects;

import java.time.LocalDate;



public class User {
    private String username,password, email, accType;
    private LocalDate signUpDate, lastPostDate;

    public User(String username,String password, String email, String accType){
        this.username = username;
        this.password = password;
        this.email = email;
        this.accType = accType;
        this.signUpDate = LocalDate.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }


    public LocalDate getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(LocalDate signUpDate) {
        this.signUpDate = signUpDate;
    }

    public LocalDate getLastPostDate() {
        return lastPostDate;
    }

    public void setLastPostDate(LocalDate lastPostDate) {
        this.lastPostDate = lastPostDate;
    }



    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", accType='" + accType + '\'' +
                ", signUpDate=" + signUpDate +
                ", lastPostDate=" + lastPostDate +
                '}';
    }
}
