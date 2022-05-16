package ro.ubb.lab11.core.model;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Customer extends BaseEntity<Long>{
    private String fullName;
    private String phoneNumber;
    private String email;


    //    public Customer(Long id, String fullName, String phoneNumber, String email) {
//        this.setId(id);
//        this.fullName = fullName;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//    public void setFullName(String name) {
//        this.fullName = name;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    @Override
//    public String toString() {
//        return super.toString() + " Customer{" +
//                "name='" + getFullName() + '\'' +
//                ", phoneNumber='" + getPhoneNumber() + '\'' +
//                ", email='" + getEmail() + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof Customer && this.getId().equals(((Customer) obj).getId());
//    }
}
