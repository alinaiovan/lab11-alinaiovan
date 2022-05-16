package ro.ubb.lab11.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.lab11.core.model.Ticket;
import ro.ubb.lab11.core.service.ITicketService;
import ro.ubb.lab11.web.converter.TicketConverter;
import ro.ubb.lab11.web.dto.TicketDTO;
import ro.ubb.lab11.web.dto.TicketsDTO;

import java.util.List;

@RestController
public class TicketController {
    public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private ITicketService ticketService;

    @Autowired
    private TicketConverter ticketConverter;

    @RequestMapping(value = "/tickets")
    TicketsDTO getTicketsFromRepository() {
        logger.trace("getAllTickets - method entered");
        List<Ticket> tickets = ticketService.getAllTickets();
        TicketsDTO ticketsDTO = new TicketsDTO(ticketConverter.convertModelsToDTOs(tickets));
        logger.trace("getAllTickets: " + tickets);
        return ticketsDTO;
    }

    @RequestMapping(value = "/tickets", method = RequestMethod.POST)
    TicketDTO addTicket(@RequestBody TicketDTO ticketDTO) {
        logger.trace("addTicket - method entered - TicketDTO: " + ticketDTO);
        var ticket = ticketConverter.convertDtoToModel(ticketDTO);
        var result = ticketService.addTicket(ticket.getCustomerId(), ticket.getFlightId(), ticket.getPrice());
        var resultModel = ticketConverter.convertModelToDto(result);
        logger.trace("addTicket - Ticket added");
        return resultModel;
    }

    @RequestMapping(value = "/tickets/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/tickets/{id}", method = RequestMethod.PUT)
    TicketDTO updateTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
        logger.trace("updateTicket - method entered - TicketDTO: " + ticketDTO);
        var ticket = ticketConverter.convertDtoToModel(ticketDTO);
        var result = ticketService.updateTicket(id, ticket.getCustomerId(), ticket.getFlightId(), ticket.getPrice());
        var resultModel = ticketConverter.convertModelToDto(result);
        logger.trace("updateTicket - Ticket updated");
        return resultModel;
    }

    @RequestMapping(value = "/tickets/filter/{price}")
    TicketsDTO getAllTicketsWithPriceLessThan(@PathVariable Long price) {
        logger.trace("getAllTicketsWithPriceLessThan - method entered - price = {}", price);
        List<Ticket> tickets = ticketService.getAllByPriceLessThan(price);
        TicketsDTO ticketsDTO = new TicketsDTO(ticketConverter.convertModelsToDTOs(tickets));
        logger.trace("getAllTicketsWithPriceLessThan: " + tickets);
        return ticketsDTO;
    }
}
