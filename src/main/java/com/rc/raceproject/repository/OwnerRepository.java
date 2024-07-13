package com.rc.raceproject.repository;

import com.rc.raceproject.entity.Owners;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owners, String> {

}
