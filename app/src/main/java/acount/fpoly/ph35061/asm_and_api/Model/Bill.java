package acount.fpoly.ph35061.asm_and_api.Model;

public class Bill {
    String _id;
    String Date;
    String Email;

    public Bill(String _id, String date, String email) {
        this._id = _id;
        Date = date;
        Email = email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
