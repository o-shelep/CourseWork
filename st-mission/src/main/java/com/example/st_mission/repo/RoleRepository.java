package com.example.st_mission.repo;


import com.example.st_mission.model.ERole;
import com.example.st_mission.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByName(ERole name);

}
