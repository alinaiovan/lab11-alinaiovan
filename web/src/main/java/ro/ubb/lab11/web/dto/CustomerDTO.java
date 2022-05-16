package ro.ubb.lab11.web.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerDTO extends BaseDTO<Long>{
    private String fullName, phoneNumber, email;
}
