package com.cloudmore.project.test.consumer.repository;

import com.cloudmore.project.test.consumer.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
