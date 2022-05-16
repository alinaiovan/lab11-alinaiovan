package ro.ubb.lab11.core.service;

import ro.ubb.lab11.core.model.Luggage;

import java.util.List;

public interface ILuggageService {
    List<Luggage> getAllLuggage();
    Luggage addLuggage(Long ticketId, Long price, String type);
    void deleteLuggage(Long id);
    Luggage updateLuggage(Long id, Long ticketId, Long price, String type);
    List<Luggage> getAllByTicketId(Long ticketId);
}
