package com.example.demo.repositories;
import com.example.demo.entities.GasMeanMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasMeanMonthRepository extends JpaRepository<GasMeanMonth, Integer> {
}
