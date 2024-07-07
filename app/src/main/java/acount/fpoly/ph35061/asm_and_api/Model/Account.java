package acount.fpoly.ph35061.asm_and_api.Model;

public class Account {
    String _id;
    String Email;
    String Password;
    String Fullname;

    String Username;

    String Phone;
    String Date;

    public Account() {
    }

    public Account(String _id, String email, String password, String fullname) {
        this._id = _id;
        Email = email;
        Password = password;
        Fullname = fullname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }
}
