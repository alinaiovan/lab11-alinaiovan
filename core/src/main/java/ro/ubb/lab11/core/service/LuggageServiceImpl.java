package ro.ubb.lab11.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.lab11.core.exceptions.AirlineException;
import ro.ubb.lab11.core.model.Luggage;
import ro.ubb.lab11.core.model.validators.LuggageValidator;
import ro.ubb.lab11.core.repository.ILuggageRepository;

import java.util.List;

@Service
public class LuggageServiceImpl implements ILuggageService {
    public static final Logger logger = LoggerFactory.getLogger(LuggageServiceImpl.class);

    @Autowired
    private LuggageValidator validator;
    @Autowired
    private ILuggageRepository luggageRepository;

    @Override
    public List<Luggage> getAllLuggage() {
        logger.trace("getAllLuggage - method entered");
        List<Luggage> luggage = luggageRepository.findAll();
        logger.trace("getAllLuggage: " + luggage);
        return luggage;
    }

    @Override
    public Luggage addLuggage(Long ticketId, Long price, String type) {
        logger.trace("addLuggage - method entered - ticketId: " + ticketId + " - price: " + price + " - type: " + type);
        Luggage luggage = new Luggage(ticketId, price, type);
        validator.validate(luggage);
        var result = luggageRepository.save(luggage);
        logger.trace("addLuggage - method finished");
        return result;
    }

    @Override
    public void deleteLuggage(Long id) {
        logger.trace("deleteLuggage - method entered - id: " + id);
        luggageRepository.findById(id)
                .ifPresentOrElse((luggage) -> luggageRepository.deleteById(id),
                        () -> {
                            throw new AirlineException("Luggage does not exist!");
                        });
        logger.trace("deleteLuggage - method finished");
    }

    @Override
    @Transactional
    public Luggage updateLuggage(Long id, Long ticketId, Long price, String type) {
        logger.trace("updateLuggage - method entered - id: " + id + " - ticketId: " + ticketId + " - price: " + price + " - type:" + type);
        Luggage luggage = new Luggage(ticketId, price, type);
        luggage.setId(id);
        validator.validate(luggage);
        luggageRepository.findById(id)
                .ifPresentOrElse((luggage1) -> {
                    luggage1.setTicketId(ticketId);
                    luggage1.setPrice(price);
                    luggage1.setType(type);
                }, () -> {
                    throw new AirlineException("Luggage does not exist!");
                });
        logger.trace("updateLuggage - method finished");
        return luggage;
    }

    @Override
    public List<Luggage> getAllByTicketId(Long ticketId) {
        logger.trace("getAllByTicketId - method entered - ticketId = {}", ticketId);
        var result = luggageRepository.findAllByTicketId(ticketId);
        logger.trace("getAllByTicketId - method finished - result = {}", result);
        return result;
    }
}
