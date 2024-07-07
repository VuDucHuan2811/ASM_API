package acount.fpoly.ph35061.asm_and_api.Model;

public class BillDetails {
    String _id;
    String BillID;
    String ProductID;
    String QuantityBill;

    public BillDetails(String _id, String billID, String productID, String quantityBill) {
        this._id = _id;
        BillID = billID;
        ProductID = productID;
        QuantityBill = quantityBill;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBillID() {
        return BillID;
    }

    public void setBillID(String billID) {
        BillID = billID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getQuantityBill() {
        return QuantityBill;
    }

    public void setQuantityBill(String quantityBill) {
        QuantityBill = quantityBill;
    }
}
