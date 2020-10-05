package com.example.demo.repositories;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    @Query("select id from User")
    List<Integer> getListOfUserId();

    @Query("select u from User u where u.cluster.id = ?1")
    List<User> getUsersByClusterId(int cluster_id);

}
