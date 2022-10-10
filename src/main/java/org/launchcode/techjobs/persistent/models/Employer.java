package org.launchcode.techjobs.persistent.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity
public class Employer extends AbstractEntity {


    @OneToMany
    @JoinColumn(name="employer_id")
    private final List<Job> jobs = new ArrayList<>();

    @NotBlank(message = "name can not be blank")
    @Size(max=80)
    private String location;

    public Employer( @NotBlank(message = "name can not be blank") @Size(max=80) String location) {
        this();
        this.location = location;
    }

    public Employer() {}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public List<Job> getJobs() {
        return jobs;
    }
}