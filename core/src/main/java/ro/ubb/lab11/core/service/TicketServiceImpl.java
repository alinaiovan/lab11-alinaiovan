package ro.ubb.lab11.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.lab11.core.repository.ILuggageRepository;
import ro.ubb.lab11.core.repository.ITicketRepository;
import ro.ubb.lab11.core.exceptions.AirlineException;
import ro.ubb.lab11.core.model.Ticket;
import ro.ubb.lab11.core.model.validators.TicketValidator;
import ro.ubb.lab11.core.repository.ICustomerRepository;
import ro.ubb.lab11.core.repository.IFlightRepository;

import java.util.List;

import static java.lang.Math.log;
import static java.lang.Math.max;

@Service
public class TicketServiceImpl implements ITicketService{
    public static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketValidator validator;
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IFlightRepository flightRepository;
    @Autowired
    private ILuggageRepository luggageRepository;

    @Override
    public List<Ticket> getAllTickets() {
        logger.trace("getAllTickets - method entered");
        List<Ticket> tickets = ticketRepository.findAll();
        logger.trace("getAllTickets: " + tickets);
        return tickets;
    }

    @Override
    public Ticket addTicket(Long customerId, Long flightId, Long price) {
        logger.trace("addTicket - method entered - customerId: "+ customerId + " - flightId: " + flightId + " - price: " + price);
        customerRepository.findById(customerId)
                .ifPresentOrElse((customer) -> {
                    flightRepository.findById(flightId)
                            .ifPresentOrElse((flight) -> {

                            }, () -> {
                                throw new AirlineException("Flight does not exist!");
                            });
                }, () -> {
                    throw new AirlineException("Customer does not exist!");
                });

        Ticket ticket = new Ticket(customerId, flightId, price);
        validator.validate(ticket);
        var result = ticketRepository.save(ticket);
        logger.trace("addTicket - method finished");
        return result;

    }

    @Override
    public void deleteTicket(Long id) {
        logger.trace("deleteTicket - method entered - id: " + id);
        luggageRepository.findAll().stream()
                .filter(luggage -> luggage.getTicketId().equals(id))
                .findAny()
                .ifPresent(ticket -> {
                    throw new AirlineException("The ticket has a luggage!");
                });

        ticketRepository.findById(id).ifPresentOrElse((ticket) ->ticketRepository.deleteById(id),() -> {
            throw new AirlineException("Ticket does not exist");
        });

        logger.trace("deleteTicket - method finished");
    }

    @Override
    @Transactional
    public Ticket updateTicket(Long id, Long customerId, Long flightId, Long price) {
        logger.trace("updateTicket - method entered - id: " + id + " - customerId: " + customerId + " - flightId: " + flightId);
        Ticket ticket = new Ticket(customerId, flightId, price);
        ticket.setId(id);
        validator.validate(ticket);
        ticketRepository.findById(id).ifPresentOrElse((ticket1) -> {
            customerRepository.findById(customerId).orElseThrow(() -> new AirlineException("Customer doesn't exist!"));
            flightRepository.findById(flightId).orElseThrow(() -> new AirlineException("Flight doesn't exist!"));
            ticket1.setCustomerId(customerId);
            ticket1.setFlightId(flightId);
            ticket1.setPrice(price);
        }, () -> {
            throw new AirlineException("Ticket does not exist!");
        });
        logger.trace("updateTicket - method finished");
        return ticket;
    }

    @Override
    public List<Ticket> getAllByPriceLessThan(Long price) {
        logger.trace("getAllByPriceLessThan - method entered - price = {}", price);
        var result = ticketRepository.findAllByPriceLessThan(price);
        logger.trace("getAllByPriceLessThan - method finished - result = {}", result);
        return result;
    }
}
