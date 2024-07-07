package acount.fpoly.ph35061.asm_and_api.Model;

public class Product {
    String _id;
    String ProductName;
    String Description;
    int Price;
    String CateID;
    String Image;

    public Product() {
    }

    public Product(String _id, String productName, String description, int price, String cateID, String image) {
        this._id = _id;
        ProductName = productName;
        Description = description;
        Price = price;
        CateID = cateID;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCateID() {
        return CateID;
    }

    public void setCateID(String cateID) {
        CateID = cateID;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

}
