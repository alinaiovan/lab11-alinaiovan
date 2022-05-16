package ro.ubb.lab11.core.model;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Ticket extends BaseEntity<Long>{
//    @Column(name="customerid")
    private Long customerId;
//    @Column(name="flightid")
    private Long flightId;
    private Long price;

//    public Ticket(Long id, Long customerId, Long flightId, Long price) {
//        this.setId(id);
//        this.customerId = customerId;
//        this.flightId = flightId;
//        this.price = price;
//    }
//
//    public Long getCustomerId() {
//        return customerId;
//    }
//    public void setCustomerId(Long tickedId) {
//        this.customerId = tickedId;
//    }
//
//    public Long getFlightId() {
//        return flightId;
//    }
//    public void setFlightId(Long flightId) {
//        this.flightId = flightId;
//    }
//
//    public Long getPrice() {
//        return price;
//    }
//    public void setPrice(Long price) {
//        this.price = price;
//    }

//    @Override
//    public String toString() {
//        return super.toString() + " Ticket{" +
//                "customerId='" + getCustomerId() + '\'' +
//                ", flightId='" + getFlightId() + '\'' +
//                ", price='" + getPrice() + '\'' +
//                '}';
//    }

//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof Ticket && this.getId().equals(((Ticket) obj).getId());
//    }
}
