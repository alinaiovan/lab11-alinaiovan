package ro.ubb.lab11.web.dto;
import lombok.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FlightDTO extends BaseDTO<Long>{
    private Timestamp departure, arrival;
}
