package ro.ubb.lab11.core.repository;

import org.springframework.stereotype.Component;
import ro.ubb.lab11.core.model.Luggage;

import java.util.List;

public interface ILuggageRepository extends IRepository<Luggage, Long> {
    List<Luggage> findAllByTicketId(Long ticketId);
}
