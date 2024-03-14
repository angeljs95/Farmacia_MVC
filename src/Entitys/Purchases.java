package Entitys;

/**
 *
 * @author AngelJs
 */
public class Purchases {

    private int id;
    private int code;
    private String productName;
    private int purchaseAmount;
    private double purchasePrice;
    private double purchaseSubtotal;
    private double total;
    private String created;
    private String supplier_name_product;
    private String purcharser;

    public Purchases(int id, int code, String productName, int purchaseAmount, double purchasePrice, double purchaseSubtotal, double total, String created, String supplier_name_product, String purcharser) {
        this.id = id;
        this.code = code;
        this.productName = productName;
        this.purchaseAmount = purchaseAmount;
        this.purchasePrice = purchasePrice;
        this.purchaseSubtotal = purchaseSubtotal;
        this.total = total;
        this.created = created;
        this.supplier_name_product = supplier_name_product;
        this.purcharser = purcharser;
    }

    public Purchases() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getPurchaseSubtotal() {
        return purchaseSubtotal;
    }

    public void setPurchaseSubtotal(double purchaseSubtotal) {
        this.purchaseSubtotal = purchaseSubtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getSupplier_name_product() {
        return supplier_name_product;
    }

    public void setSupplier_name_product(String supplier_name_product) {
        this.supplier_name_product = supplier_name_product;
    }

    public String getPurcharser() {
        return purcharser;
    }

    public void setPurcharser(String purcharser) {
        this.purcharser = purcharser;
    }

}
