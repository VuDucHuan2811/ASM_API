package acount.fpoly.ph35061.asm_and_api.Model;

public class Category {
    String _id;
    String CateName;

    public Category(String _id, String cateName) {
        this._id = _id;
        CateName = cateName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCateName() {
        return CateName;
    }

    public void setCateName(String cateName) {
        CateName = cateName;
    }
}
