package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.demo.entities.Log;
@Repository
public interface LogRepository extends JpaRepository<Log,Integer>{
    @Query("select l from Log l")
    List <Log> getLogs();
}
