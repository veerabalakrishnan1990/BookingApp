package com.trainbookingapp.net.service;

import com.trainbookingapp.net.model.*;
import com.trainbookingapp.net.repository.TrainRepository;
import com.trainbookingapp.net.repository.UserRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final RedissonClient redissonClient;

    @Autowired
    public BookingService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public String bookTicket(BookingRequest bookingRequest) {
        String seatNumber = bookingRequest.getSeatNumber();
        Optional<Train> train = trainRepository.findById(bookingRequest.getTrainId());
        Optional<Seat> seat = train.get().getSections().stream()
                .flatMap(section -> section.getSeats().stream())
                .filter(seat1 -> seatNumber.equals(seat1.getSeatNumber()))
                .findFirst();
        Optional<User> user = userRepository.findById(bookingRequest.getUserId());

        // Define a lock key
        String lockKey = "lock:seat:" + seatNumber;

        // Get the distributed lock
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // Check seat availability
            if (seat.isPresent() && seat.get().getState().equalsIgnoreCase("AvailableState")) {
                // Try to acquire the lock with a timeout
                if (lock.tryLock(10, TimeUnit.SECONDS)) {
                    // Perform the booking logic here
                    Booking booking = new Booking();
                    booking.setId(UUID.randomUUID().toString());
                    booking.setTrainId(train.get().getId());
                    booking.setArrivalStation(bookingRequest.getArrivalStation());
                    booking.setDepartureStation(bookingRequest.getDepartureStation());
                    booking.setSeatNumber(seat.get().getSeatNumber());
                    booking.setUser(user.get().getId());

                    AvailableState availableState = new AvailableState(trainRepository);
                    // Delegate the bookTicket operation to the current state
                    availableState.bookTicket(booking);
                    return "Booking request processed for seat: " + bookingRequest.getSeatNumber();

                } else {
                    // Unable to acquire the lock, meaning someone else is booking the same seat
                    System.out.println("Seat " + seatNumber + " is already being booked by another user.");
                    return "Seat " + seatNumber + " is already being booked by another user.";
                }
            } else {
                System.out.println("Seat " + seatNumber + " is already booked by another user.");
                return "Seat " + seatNumber + " is already booked by another user.";
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Release the lock
            if (Objects.nonNull(lock) && lock.isHeldByCurrentThread()) {
                lock.forceUnlock();
                System.out.println("Lock released.");
            } else {
                System.out.println("Lock not held by current thread.");
            }
        }
        return "Failed to book the seat: " + seatNumber;
    }

    public String cancelBooking(CancelRequest cancelRequest) {
        Optional<Train> train = trainRepository.findById(cancelRequest.getTrainId());
        Optional<Seat> seatObj = train.get().getSections().stream()
                .flatMap(section -> section.getSeats().stream())
                .filter(seat -> cancelRequest.getSeatNumber().equals(seat.getSeatNumber()))
                .findFirst();

        Booking booking = seatObj.get().getBooking();

        if (Objects.nonNull(booking) && seatObj.get().getState().equalsIgnoreCase("BookedState")) {
            BookedState bookedState = new BookedState(trainRepository);
            booking.setTrainId(train.get().getId());
            bookedState.cancelBooking(booking);
            return "Cancellation request processed for seat: " + cancelRequest.getSeatNumber();
        } else {
            return "No booking found for seat: " + booking.getSeatNumber();
        }
    }

   public List<Seat> getBookingInfo(BookingInfoRequest bookingInfoRequest) {
       Optional<Train> train = trainRepository.findById(bookingInfoRequest.getTrainId());
       List<Seat> seats = train.get().getSections().stream()
               .flatMap(section -> section.getSeats().stream().filter(seat -> seat.getState().equalsIgnoreCase("BookedState")))
               .filter(seat -> seat.getBooking() != null && bookingInfoRequest.getUserId().equals(seat.getBooking().getUser()))
               .collect(Collectors.toList());
       return seats;
    }
}
