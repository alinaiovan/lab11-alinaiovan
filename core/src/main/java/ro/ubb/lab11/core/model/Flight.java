package ro.ubb.lab11.core.model;

import java.sql.Timestamp;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "flights")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Flight extends BaseEntity<Long>{
    private Timestamp departure;
    private Timestamp arrival;

//    public Flight(Long id, Timestamp departure, Timestamp arrival) {
//        this.setId(id);
//        this.departureTime = departure;
//        this.arrivalTime = arrival;
//    }
//
//    public Timestamp getDepartureTime() {
//        return this.departureTime;
//    }
//
//    public Timestamp getArrivalTime() {
//        return this.arrivalTime;
//    }
//
//    public void setDepartureTime(Timestamp time) {
//        this.departureTime = time;
//    }
//
//    public void getArrivalTime(Timestamp time) {
//        this.arrivalTime = time;
//    }

//    @Override
//    public String toString() {
//        return super.toString() +
//                "Flight{departure time: " + this.departureTime +
//                "; arrival time: " + this.arrivalTime + "}";
//    }

//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof Flight && this.getId().equals(((Flight) obj).getId());
//    }
}
