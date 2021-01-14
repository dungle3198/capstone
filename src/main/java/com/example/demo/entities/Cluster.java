package com.example.demo.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    List<ClusterDetail> clusterDetails = new ArrayList<>();

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.PERSIST)
    List<User> users = new ArrayList<>();

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
    public List<ClusterDetail> getClusterDetails() {
        return clusterDetails;
    }

    public void setClusterDetails(List<ClusterDetail> clusterDetails) {
        this.clusterDetails = clusterDetails;
    }

    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
