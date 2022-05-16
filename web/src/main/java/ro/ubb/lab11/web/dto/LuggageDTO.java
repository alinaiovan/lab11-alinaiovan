package ro.ubb.lab11.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LuggageDTO extends BaseDTO<Long>{
    private Long ticketId, price;
    private String type;
}
