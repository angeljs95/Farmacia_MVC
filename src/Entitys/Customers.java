package Entitys;

/**
 *
 * @author AngelJs
 */
public class Customers {
    
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String created;
    private String updated;

    public Customers(int id, String name, String address, String phone, String email, String created, String updated) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.created = created;
        this.updated = updated;
    }
    
    public Customers(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdate(String updated) {
        this.updated = updated;
    }
}
