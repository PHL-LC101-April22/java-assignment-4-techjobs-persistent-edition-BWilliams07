package org.launchcode.techjobs.persistent.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {

    public Employer(){}
    @NotNull
    @NotBlank
    @Size(min=3, max = 60)
    private String location;

    @OneToMany
    @JoinColumn(name="employer_id")
    private List<Job> jobs= new ArrayList<>();

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location=location;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}