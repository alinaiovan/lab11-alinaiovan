package ro.ubb.lab11.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Ticket;
import ro.ubb.lab11.web.dto.TicketDTO;

@Component
public class TicketConverter extends BaseConverter<Long, Ticket, TicketDTO> {

    @Override
    public Ticket convertDtoToModel(TicketDTO dto) {
        var model = new Ticket();
        model.setId(dto.getId());
        model.setCustomerId(dto.getCustomerId());
        model.setFlightId(dto.getFlightId());
        model.setPrice(dto.getPrice());
        return model;
    }

    @Override
    public TicketDTO convertModelToDto(Ticket ticket) {
        var ticketDTO = new TicketDTO(ticket.getCustomerId(), ticket.getFlightId(), ticket.getPrice());
        ticketDTO.setId(ticket.getId());
        return ticketDTO;
    }
}
