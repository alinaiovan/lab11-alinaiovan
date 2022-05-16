package ro.ubb.lab11.web.dto;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TicketDTO extends BaseDTO<Long>{
    private Long customerId, flightId, price;

}
