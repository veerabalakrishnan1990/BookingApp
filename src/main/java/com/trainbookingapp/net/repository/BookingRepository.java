package com.trainbookingapp.net.repository;

import com.trainbookingapp.net.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, String> {
}
