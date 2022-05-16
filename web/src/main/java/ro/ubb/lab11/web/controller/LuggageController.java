package ro.ubb.lab11.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.lab11.core.model.Luggage;
import ro.ubb.lab11.core.service.ILuggageService;
import ro.ubb.lab11.web.converter.LuggageConverter;
import ro.ubb.lab11.web.dto.LuggageDTO;
import ro.ubb.lab11.web.dto.LuggagesDTO;

import java.util.List;

@RestController
public class LuggageController {
    public static final Logger logger = LoggerFactory.getLogger(LuggageController.class);

    @Autowired
    private ILuggageService luggageService;

    @Autowired
    private LuggageConverter luggageConverter;

    @RequestMapping(value = "/luggage")
    LuggagesDTO getLuggageFromRepository() {
        logger.trace("getAllLuggage - method entered");
        List<Luggage> luggage = luggageService.getAllLuggage();
        LuggagesDTO luggagesDTO = new LuggagesDTO(luggageConverter.convertModelsToDTOs(luggage));
        logger.trace("getAllLuggage: " + luggage);
        return luggagesDTO;
    }

    @RequestMapping(value = "/luggage", method = RequestMethod.POST)
    LuggageDTO addLuggage(@RequestBody LuggageDTO luggageDTO) {
        logger.trace("addLuggage - method entered - LuggageDTO: " + luggageDTO);
        var luggage = luggageConverter.convertDtoToModel(luggageDTO);
        var result = luggageService.addLuggage(luggage.getTicketId(), luggage.getPrice(), luggage.getType());
        var resultModel = luggageConverter.convertModelToDto(result);
        logger.trace("addLuggage - Luggage added");
        return resultModel;
    }

    @RequestMapping(value = "/luggage/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteLuggage(@PathVariable Long id) {
        luggageService.deleteLuggage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/luggage/{id}", method = RequestMethod.PUT)
    LuggageDTO updateLuggage(@PathVariable Long id, @RequestBody LuggageDTO luggageDTO) {
        logger.trace("updateLuggage - method entered - LuggageDTO: " + luggageDTO);
        var luggage = luggageConverter.convertDtoToModel(luggageDTO);
        var result = luggageService.updateLuggage(id, luggage.getTicketId(), luggage.getPrice(), luggage.getType());
        var resultModel = luggageConverter.convertModelToDto(result);
        logger.trace("updateLuggage - Luggage updated");
        return resultModel;
    }
    @RequestMapping(value = "/luggage/filter/{ticketId}")
    LuggagesDTO filterLuggageByTicket(@PathVariable Long ticketId){
        logger.trace("filterLuggageByTicket - method entered - ticketId = {}", ticketId);
        List<Luggage> luggage = luggageService.getAllByTicketId(ticketId);
        LuggagesDTO luggageDTO = new LuggagesDTO(luggageConverter.convertModelsToDTOs(luggage));
        logger.trace("filterLuggageByTicket: " + luggage);
        return luggageDTO;
    }

}
