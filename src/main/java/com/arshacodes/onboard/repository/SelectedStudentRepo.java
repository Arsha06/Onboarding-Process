package com.arshacodes.onboard.repository;

import com.arshacodes.onboard.entity.SelectedStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedStudentRepo extends JpaRepository<SelectedStudent, Long> {
}
