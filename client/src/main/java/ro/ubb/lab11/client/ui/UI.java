package ro.ubb.lab11.client.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ro.ubb.lab11.web.dto.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

@Component
public class UI {
    @Autowired
    private RestTemplate restTemplate;
    private final Map<Integer, Runnable> menuTable = new HashMap<>();
    String customersUrl = "http://localhost:8080/api/customers";
    String flightsUrl = "http://localhost:8080/api/flights";
    String ticketsUrl = "http://localhost:8080/api/tickets";
    String luggageUrl = "http://localhost:8080/api/luggage";

    static void printMenu(){
        System.out.println("1. Add a costumer");
        System.out.println("2. Show customers");
        System.out.println("3. Delete customer");
        System.out.println("4. Update customer\n");

        System.out.println("5. Add flight");
        System.out.println("6. Show flights");
        System.out.println("7. Delete flight");
        System.out.println("8. Update flight\n");

        System.out.println("9. Add ticket");
        System.out.println("10. Show tickets");
        System.out.println("11. Delete ticket");
        System.out.println("12. Update ticket\n");

        System.out.println("13. Add luggage");
        System.out.println("14. Show luggage");
        System.out.println("15. Delete luggage");
        System.out.println("16. Update luggage\n");

        System.out.println("17. Filter customers by name");
        System.out.println("18. Get flights sorted by departure");
        System.out.println("19. Get tickets less than price");
        System.out.println("20. Get luggage filtered by ticket\n");


        System.out.println("0. Exit\n");
    }

    public void runProgram() {
        menuTable.put(1, this::addCustomer);
        menuTable.put(2, this::showCustomers);
        menuTable.put(3, this::deleteCustomer);
        menuTable.put(4, this::updateCustomer);
        menuTable.put(5, this::addFlight);
        menuTable.put(6, this::showFlights);
        menuTable.put(7, this::deleteFlight);
        menuTable.put(8, this::updateFlight);
        menuTable.put(9, this::addTicket);
        menuTable.put(10, this::showTickets);
        menuTable.put(11, this::deleteTicket);
        menuTable.put(12, this::updateTicket);
        menuTable.put(13, this::addLuggage);
        menuTable.put(14, this::showLuggage);
        menuTable.put(15, this::deleteLuggage);
        menuTable.put(16, this::updateLuggage);
        menuTable.put(17, this::filterCustomersByFullName);
        menuTable.put(18, this::getFlightsSortedByDeparture);
        menuTable.put(19, this::getTicketsLessThan);
        menuTable.put(20, this::getAllLuggageByTicket);
        while(true) {
            printMenu();
            try {
                int choice = readNumberFromConsole();
                if (choice == 0)
                    break;
                Runnable toRun = menuTable.get(choice);
                if (toRun == null) {
                    System.out.println("Bad choice");
                    continue;
                }
                toRun.run();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Invalid integer");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    int readNumberFromConsole() {
        Scanner stdin = new Scanner(System.in);
        return stdin.nextInt();
    }

    void addCustomer() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Full Name: ");
        String name = stdin.next();
        System.out.println("Phone number: ");
        String pnb = stdin.next();
        System.out.println("Email: ");
        String email = stdin.next();
       try{
           CustomerDTO customerDTO= new CustomerDTO(name, pnb, email);
           restTemplate.postForObject(customersUrl, customerDTO, CustomerDTO.class);
           System.out.println("Customer added successfully");
       } catch (HttpStatusCodeException ex) {
           System.out.println(ex.getMessage());
           System.out.println(ex.getStatusCode());

           System.out.println("Cannot add customer!");
       }
    }

    void showCustomers() {
        CustomersDTO customersDTO = restTemplate.getForObject(customersUrl, CustomersDTO.class);
        assert customersDTO != null;
        customersDTO.getCustomers().forEach(System.out::println);
    }

    void deleteCustomer(){
        Scanner stdin = new Scanner(System.in);
        System.out.println("Customer id: ");
        Long id = stdin.nextLong();
        try{
            restTemplate.delete(customersUrl + "/{id}", id);
            System.out.println("Customer deleted successfully");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot delete customer!");
        }
    }

    void updateCustomer(){
        Scanner stdin = new Scanner(System.in);
        System.out.println("Id: ");
        Long id = Long.parseLong(stdin.nextLine());
        System.out.println("Full Name: ");
        String name = stdin.nextLine().strip();
        System.out.println("Phone number: ");
        String pnb = stdin.nextLine().strip();
        System.out.println("Email: ");
        String email = stdin.nextLine().strip();
       try{
           CustomerDTO customerDTO = new CustomerDTO(name, pnb, email);
           restTemplate.put(customersUrl + "/{id}", customerDTO, id);
           System.out.println("Customer updated successfully.");
       }catch (HttpStatusCodeException ex) {
           System.out.println("Cannot update customer!");
       }
    }

    private void filterCustomersByFullName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Full name: ");
        String fullName = scanner.next();
        try {
            CustomersDTO customersDTO = restTemplate.getForObject(customersUrl + "/filter/{fullName}", CustomersDTO.class, fullName);
            if (customersDTO == null)
                System.out.println("No customers with the given name!");
            else
                customersDTO.getCustomers().forEach(System.out::println);
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot get customers!");
        }
    }

    void addFlight(){
        Scanner stdin = new Scanner(System.in);
        System.out.println("Departure time (dd.MM.yyyy HH:mm): ");
        LocalDateTime departure = LocalDateTime.parse(stdin.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        System.out.println("Arrival time (dd.MM.yyyy HH:mm): ");
        LocalDateTime arrival = LocalDateTime.parse(stdin.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        try{
            Timestamp d = Timestamp.valueOf(departure);
            Timestamp a = Timestamp.valueOf(arrival);
            FlightDTO flightDTO= new FlightDTO(d, a);
            restTemplate.postForObject(flightsUrl, flightDTO, FlightDTO.class);
            System.out.println("Flight added successfully");
        } catch (NullPointerException e) {
            System.out.println("Wrong time format!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot add customer!");
        }
    }

    void showFlights(){
        FlightsDTO flightsDTO = restTemplate.getForObject(flightsUrl, FlightsDTO.class);
        assert flightsDTO != null;
        flightsDTO.getFlights().forEach(System.out::println);
    }

    void deleteFlight(){
        Scanner stdin = new Scanner(System.in);
        System.out.println("Flight id: ");
        Long id = stdin.nextLong();
        try{
            restTemplate.delete(flightsUrl + "/{id}", id);
            System.out.println("Flight deleted successfully");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot delete flight!");
        }
    }

    void updateFlight(){
        Scanner stdin = new Scanner(System.in);
        System.out.println("Id: ");
        Long id = Long.parseLong(stdin.nextLine());
        System.out.println("Departure time (dd.MM.yyyy HH:mm): ");
        LocalDateTime departure = LocalDateTime.parse(stdin.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        System.out.println("Arrival time (dd.MM.yyyy HH:mm): ");
        LocalDateTime arrival = LocalDateTime.parse(stdin.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        try{
            Timestamp d = Timestamp.valueOf(departure);
            Timestamp a = Timestamp.valueOf(arrival);
            FlightDTO flightDTO= new FlightDTO(d, a);
            restTemplate.put(flightsUrl + "/{id}", flightDTO, id);
            System.out.println("Flight updated successfully.");
        } catch (NullPointerException e) {
            System.out.println("Wrong time format!");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update flight!");
        }
    }

    private void getFlightsSortedByDeparture(){
        try{
            FlightsDTO flightsDTO = restTemplate.getForObject(flightsUrl + "/sort/departure", FlightsDTO.class);
            if(flightsDTO == null)
                System.out.println("No flights!");
            else
                flightsDTO.getFlights().forEach(System.out::println);
        }catch (HttpStatusCodeException ex){
            System.out.println("Cannot get flights!");
        }
    }

    void addTicket() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Customer id: ");
        Long customerId = Long.parseLong(stdin.nextLine());
        System.out.println("Flight id: ");
        Long flightId = Long.parseLong(stdin.nextLine());
        System.out.println("Price: ");
        Long price = Long.parseLong(stdin.nextLine());
        try{
            TicketDTO ticketDTO= new TicketDTO(customerId, flightId, price);
            restTemplate.postForObject(ticketsUrl, ticketDTO, TicketDTO.class);
            System.out.println("Ticket added successfully");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot add ticket!");
        }
    }

    void showTickets() {
        TicketsDTO ticketsDTO = restTemplate.getForObject(ticketsUrl, TicketsDTO.class);
        assert ticketsDTO != null;
        ticketsDTO.getTickets().forEach(System.out::println);
    }

    void deleteTicket(){
        Scanner stdin = new Scanner(System.in);
        System.out.println("Ticket id: ");
        Long id = stdin.nextLong();
        try{
            restTemplate.delete(ticketsUrl + "/{id}", id);
            System.out.println("Ticket deleted successfully");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot delete ticket!");
        }
    }

    void updateTicket(){
        Scanner stdin = new Scanner(System.in);
        System.out.println("Id: ");
        Long id = Long.parseLong(stdin.nextLine());
        System.out.println("Customer id: ");
        Long customerId = Long.parseLong(stdin.nextLine());
        System.out.println("Flight id: ");
        Long flightId = Long.parseLong(stdin.nextLine());
        System.out.println("Price: ");
        Long price = Long.parseLong(stdin.nextLine());
        try{
            TicketDTO ticketDTO = new TicketDTO(customerId, flightId, price);
            restTemplate.put(ticketsUrl + "/{id}", ticketDTO, id);
            System.out.println("Ticket updated successfully.");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update ticket!");
        }
    }

    void getTicketsLessThan(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter price: ");
        Long price = scanner.nextLong();
        try{
            TicketsDTO ticketsDTO = restTemplate.getForObject(ticketsUrl+"/filter/{price}",TicketsDTO.class, price);
            if(ticketsDTO == null)
                System.out.println("No tickets with price less than the give price!");
            else
                ticketsDTO.getTickets().forEach(System.out::println);
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot filter tickets!");
        }
    }

    void addLuggage() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Ticket id: ");
        Long ticketId = Long.parseLong(stdin.nextLine());
        System.out.println("Price: ");
        Long price = Long.parseLong(stdin.nextLine());
        System.out.println("Type: ");
        String type = stdin.nextLine().strip();
        try{
            LuggageDTO luggageDTO = new LuggageDTO(ticketId, price, type);
            restTemplate.postForObject(luggageUrl, luggageDTO, LuggageDTO.class);
            System.out.println("Luggage added successfully");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot add luggage!");
        }
    }

    void showLuggage() {
        LuggagesDTO luggagesDTO = restTemplate.getForObject(luggageUrl, LuggagesDTO.class);
        assert luggagesDTO != null;
        luggagesDTO.getLuggage().forEach(System.out::println);
    }

    void deleteLuggage(){
        Scanner stdin = new Scanner(System.in);
        System.out.println("Luggage id: ");
        Long id = stdin.nextLong();
        try{
            restTemplate.delete(luggageUrl + "/{id}", id);
            System.out.println("Luggage deleted successfully");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot delete luggage!");
        }
    }

    void updateLuggage(){
        Scanner stdin = new Scanner(System.in);
        System.out.println("Id: ");
        Long id = Long.parseLong(stdin.nextLine());
        System.out.println("Ticket id: ");
        Long ticketId = Long.parseLong(stdin.nextLine());
        System.out.println("Price: ");
        Long price = Long.parseLong(stdin.nextLine());
        System.out.println("Type: ");
        String type = stdin.nextLine().strip();
        try{
            LuggageDTO luggageDTO = new LuggageDTO(ticketId, price, type);
            restTemplate.put(luggageUrl + "/{id}", luggageDTO, id);
            System.out.println("Luggage updated successfully.");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update luggage!");
        }
    }

    void getAllLuggageByTicket(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ticket id: ");
        Long ticketId = scanner.nextLong();
        try{
            LuggagesDTO luggagesDTO = restTemplate.getForObject(luggageUrl+"/filter/{ticketId}",LuggagesDTO.class, ticketId);
            if(luggagesDTO == null)
                System.out.println("No luggage with this ticket id found!");
            else
                luggagesDTO.getLuggage().forEach(System.out::println);
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot filter luggage!");
        }
    }

}
