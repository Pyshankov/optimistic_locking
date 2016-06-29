package com.example.domain;



import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by pyshankov on 24.06.16.
 */

@Entity
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Column(name = "model",unique = true, nullable = false)
    private String model;
    private String description;

    @Version
    private Integer version;

    @Transient
    private Integer lastVisibleVersion;

    public Car(){};

    public Car( String model, String des){
        this.model=model;
        this.description=des;
    };

    @PostLoad
    public void postLoad(){
        lastVisibleVersion=version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getLastVisibleVersion() {
        return lastVisibleVersion;
    }

    public void setLastVisibleVersion(Integer lastVisibleVersion) {
        this.lastVisibleVersion = lastVisibleVersion;
    }


    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", description='" + description + '\'' +
                ", version=" + version +
                ", lastVisibleVersion=" + lastVisibleVersion +
                '}';
    }
}
