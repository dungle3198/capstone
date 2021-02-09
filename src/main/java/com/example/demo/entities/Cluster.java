package com.example.demo.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "cluster")
public class Cluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
    @NotFound(action= NotFoundAction.IGNORE)
    List<ClusterStats> clusterStats = new ArrayList<>();

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
    @NotFound(action= NotFoundAction.IGNORE)
    //List<User> users = new ArrayList<>();
    List<ClusterDetail> clusterDetails = new ArrayList<>();

    @Column(name = "primary_user")
    private int userId;

    public Cluster()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public List<ClusterStats> getClusterDetails() {
        return clusterStats;
    }

    public void setClusterDetails(List<ClusterStats> clusterStats) {
        this.clusterStats = clusterStats;
    }

//    @JsonIgnore
//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }

    @JsonIgnore
    public List<ClusterDetail> getGroups() {
        return clusterDetails;
    }

    public void setGroups(List<ClusterDetail> clusterDetails) {
        this.clusterDetails = clusterDetails;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
