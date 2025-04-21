package com.ansh.plannerCrud.Repositories;

import com.ansh.plannerCrud.Modules.Bookings;
import com.ansh.plannerCrud.Modules.Users;

import com.ansh.plannerCrud.Modules.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Book;
import java.util.Optional;

public interface BookingsRepo extends JpaRepository<Bookings, Integer> {

Optional<Bookings> findByUsers(Users users);
//    @Modifying
//    @Query("DELETE FROM Venue_Products vp WHERE vp.venue.id = :venueId")
//    void deleteVenueReferences( int venueId);
//@Modifying
//@Query("DELETE FROM Bookings b WHERE :venue MEMBER OF b.venueList")
//void deleteVenueReferences(@Param("venue") Venue venue);
}
