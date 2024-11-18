package lk.ijse.gdse71.dreamlandkids.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDTO {
    private String customerId;
    private String name;
    private String nic;
    private String email;
    private String phone;

//    public CustomerDTO(String customerId, String name, String nic, String email, String phone) {
//        this.customerId = customerId;
//        this.name = name;
//        this.nic = nic;
//        this.email = email;
//        this.phone = phone;
//    }
//
//    public CustomerDTO() {
//    }
//
//    public String getCustomerId() {
//        return customerId;
//    }
//
//    public void setId(String customerId) {
//        this.customerId = customerId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getNic() {
//        return nic;
//    }
//
//    public void setNic(String nic) {
//        this.nic=nic;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    @Override
//    public String toString() {
//        return "CustomerTM{" +
//                "customerId='" + customerId + '\'' +
//                ", name='" + name + '\'' +
//                ", nic='" + nic + '\'' +
//                ", email='" + email + '\'' +
//                ", phone='" + phone + '\'' +
//                '}';
//    }
}
