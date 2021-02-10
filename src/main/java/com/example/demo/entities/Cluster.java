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
    public List<ClusterStats> getClusterStats() {
        return clusterStats;
    }

    public void setClusterStats(List<ClusterStats> clusterStats) {
        this.clusterStats = clusterStats;
    }

    @JsonIgnore
    public List<ClusterDetail> getClusterDetails() {
        return clusterDetails;
    }

    public void setClusterDetails(List<ClusterDetail> clusterDetails) {
        this.clusterDetails = clusterDetails;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
