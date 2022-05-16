package ro.ubb.lab11.core.model;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "luggage")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

public class Luggage extends BaseEntity<Long>{
//    @Column(name = "ticketid")
    private Long ticketId;
    private Long price;
    private String type;

//    public Luggage(Long id, Long tickedId, Long price, String type) {
//        this.setId(id);
//        this.ticketId = tickedId;
//        this.price = price;
//        this.type = type;
//    }
//
//    public Long getTicketId() {
//        return ticketId;
//    }
//    public void setTicketId(Long tickedId) {
//        this.ticketId = tickedId;
//    }
//
//    public Long getPrice() {
//        return price;
//    }
//    public void setPrice(Long price) {
//        this.price = price;
//    }
//
//    public String getType() {
//        return type;
//    }
//    public void setType(String type) {
//        this.type = type;
//    }

//    @Override
//    public String toString() {
//        return super.toString() + " Luggage{" +
//                "ticketId='" + getTicketId() + '\'' +
//                ", price='" + getPrice() + '\'' +
//                ", type='" + getType() + '\'' +
//                '}';
//    }

//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof Luggage && this.getId().equals(((Luggage) obj).getId());
//    }
}
