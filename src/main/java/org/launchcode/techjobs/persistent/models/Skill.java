package org.launchcode.techjobs.persistent.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity
public class Skill extends AbstractEntity {


    @ManyToMany(mappedBy = "skills")
    private List<Job> jobs = new ArrayList<>();
    @NotBlank(message = "description can not be blank")
    @Size(max=200)
    private String description;

    public Skill( @NotBlank(message = "description can not be blank") @Size(max=200) String description) {
        this.description = description;
    }

    public Skill() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}