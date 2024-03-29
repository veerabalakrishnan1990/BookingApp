/**
 * The BookedState class represents the state of a seat when it is already booked.
 */
package com.trainbookingapp.net.service;

import com.trainbookingapp.net.model.Booking;
import com.trainbookingapp.net.model.Train;
import com.trainbookingapp.net.repository.TrainRepository;

import java.util.Optional;

/**
 * The BookedState class represents the state of a seat when it is already booked.
 */
public class BookedState implements BookingState {
    private final TrainRepository trainRepository;

    /**
     * Constructor for the BookedState class.
     * @param trainRepository The TrainRepository instance for accessing train data.
     */
    public BookedState(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    /**
     * Books a ticket for the provided booking.
     * No-operation, as the seat is already booked.
     * @param booking The Booking object representing the booking to be made.
     */
    @Override
    public void bookTicket(Booking booking) {
        // No-operation, as the seat is already booked
    }

    /**
     * Cancels a booking for the provided booking.
     * Moves the seat to the Available state on cancellation.
     * @param booking The Booking object representing the booking to be cancelled.
     */
    @Override
    public void cancelBooking(Booking booking) {
        String seatNumber = booking.getSeatNumber();
        // Move to Available state on cancellation
        AvailableState availableState = new AvailableState(trainRepository);
        changeState(booking, availableState);
        System.out.println("Canceling booking for seat: " + seatNumber);
    }

    /**
     * Changes the state of the seat to the new state provided.
     * Updates the corresponding booking state.
     * @param booking The Booking object representing the booking.
     * @param newState The new state to change to.
     */
    private void changeState(Booking booking, BookingState newState) {
        String seatNumber =  booking.getSeatNumber();
        Optional<Train> train = trainRepository.findById(booking.getTrainId());
        // Perform state transition logic
        System.out.println("Changing seat " + seatNumber + " state to " + newState.getClass().getSimpleName());

        // Persist the state in Redis
        train.get().getSections().stream()
                .flatMap(section -> section.getSeats().stream())
                .filter(seat -> seatNumber.equals(seat.getSeatNumber()))
                .findFirst()
                .ifPresent(seat -> {
                    seat.setState(newState.getClass().getSimpleName());
                    // Update the corresponding booking state
                    seat.setBooking(booking);
                });

        trainRepository.save(train.get());
    }
}
