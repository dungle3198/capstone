package com.example.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "cluster_detail")
public class ClusterDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "cluster_id")
    private Cluster cluster;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ClusterDetail(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
