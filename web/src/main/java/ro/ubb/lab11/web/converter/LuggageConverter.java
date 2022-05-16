package ro.ubb.lab11.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Luggage;
import ro.ubb.lab11.web.dto.LuggageDTO;

@Component
public class LuggageConverter extends BaseConverter<Long, Luggage, LuggageDTO> {

    @Override
    public Luggage convertDtoToModel(LuggageDTO dto) {
        var model = new Luggage();
        model.setId(dto.getId());
        model.setTicketId(dto.getTicketId());
        model.setPrice(dto.getPrice());
        model.setType(dto.getType());
        return model;
    }

    @Override
    public LuggageDTO convertModelToDto(Luggage luggage) {
       var luggageDTO = new LuggageDTO(luggage.getTicketId(), luggage.getPrice(), luggage.getType());
       luggageDTO.setId(luggage.getId());
       return luggageDTO;
    }
}
