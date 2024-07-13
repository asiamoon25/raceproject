package com.rc.raceproject.repository;

import com.rc.raceproject.entity.Horses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HorseRepository extends JpaRepository<Horses, String> {
    Optional<Horses> findByHrNo(@Param("hrNo") String hrNo );
}
