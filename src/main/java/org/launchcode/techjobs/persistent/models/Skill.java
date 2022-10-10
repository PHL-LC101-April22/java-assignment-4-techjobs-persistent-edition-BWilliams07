package org.launchcode.techjobs.persistent.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {

    @ManyToMany(mappedBy = "skills")
    private final List<Job> jobs= new ArrayList<>();

    public Skill(){}


    @NotNull
    @NotBlank
    @Size(min=3, max = 600)
    private String description;

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }
    public List<Job> getJobs() {
        return jobs;
    }
}