package com.example.test_ms_sql.repo;

import com.example.test_ms_sql.domain.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<Information,Integer> {
}
