package com.rc.raceproject.repository;

import com.rc.raceproject.entity.Trainers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainers, String> {

}
