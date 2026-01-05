package com.TaskManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagement.Entity.Lable;

@Repository
public interface LableRepository extends JpaRepository<Lable,Long> {

	Optional<Lable>findByLableName(String lableName);
}