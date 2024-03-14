
package Entitys;

/**
 *
 * @author AngelJs
 */
public class Sales {
    
    private int id;
    private String sale_date;
    private double total;
    private int customer_id;
    private int employee_id;
    private String created;
    private String sale_employee_name;
    private String sale_customer_name;

    public Sales(int id, String sale_date, double total, int customer_id, int employee_id, String created, String sale_employee_name,
                    String sale_customer_name ) {
        this.id=id;
        this.sale_date = sale_date;
        this.total = total;
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.created= created;
        this.sale_employee_name= sale_employee_name;
        this.sale_customer_name=sale_customer_name;
        
    }
    
    public Sales(){
        
    }

    public String getSale_employee_name() {
        return sale_employee_name;
    }

    public void setSale_employee_name(String sale_employee_name) {
        this.sale_employee_name = sale_employee_name;
    }

    public String getSale_customer_name() {
        return sale_customer_name;
    }

    public void setSale_customer_name(String sale_customer_name) {
        this.sale_customer_name = sale_customer_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getSale_date() {
        return sale_date;
    }

    public void setSale_date(String sale_date) {
        this.sale_date = sale_date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
    
    
    
}
