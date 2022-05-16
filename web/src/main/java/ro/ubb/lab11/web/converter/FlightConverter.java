package ro.ubb.lab11.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Flight;
import ro.ubb.lab11.web.dto.FlightDTO;

@Component
public class FlightConverter extends BaseConverter<Long, Flight, FlightDTO> {
    @Override
    public Flight convertDtoToModel(FlightDTO dto) {
        var model = new Flight();
        model.setId(dto.getId());
        model.setDeparture(dto.getDeparture());
        model.setArrival(dto.getArrival());
        return model;
    }

    @Override
    public FlightDTO convertModelToDto(Flight flight) {
        var flightDTO = new FlightDTO(flight.getDeparture(), flight.getArrival());
        flightDTO.setId(flight.getId());
        return flightDTO;
    }
}
