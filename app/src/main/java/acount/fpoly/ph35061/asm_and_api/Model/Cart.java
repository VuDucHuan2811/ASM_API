package acount.fpoly.ph35061.asm_and_api.Model;

public class Cart {
    String _id;
    String ProductName;
    String Image;
    String Price;
    String Quantity;

    public Cart(String _id, String productName, String image, String price, String quantity) {
        this._id = _id;
        ProductName = productName;
        Image = image;
        Price = price;
        Quantity = quantity;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
