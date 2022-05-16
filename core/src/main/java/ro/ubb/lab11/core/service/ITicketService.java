package ro.ubb.lab11.core.service;

import ro.ubb.lab11.core.model.Ticket;

import java.util.List;

public interface ITicketService {
    List<Ticket> getAllTickets();
    Ticket addTicket(Long customerId, Long flightId, Long price);
    void deleteTicket(Long id);
    Ticket updateTicket(Long id, Long customerId, Long flightId, Long price);
    List<Ticket> getAllByPriceLessThan(Long price);
}
