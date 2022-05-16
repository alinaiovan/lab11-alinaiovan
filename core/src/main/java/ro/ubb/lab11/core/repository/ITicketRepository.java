package ro.ubb.lab11.core.repository;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Ticket;

import java.util.List;

public interface ITicketRepository extends IRepository<Ticket, Long> {
    List<Ticket> findAllByPriceLessThan(Long price);
}