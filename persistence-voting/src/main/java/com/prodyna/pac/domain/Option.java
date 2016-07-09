package com.prodyna.pac.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by bjoern on 07.03.16.
 */
@NodeEntity
public class Option {
    @GraphId
    Long optionId;

    String description;

    @Relationship(type = "OPTION_CREATOR", direction = Relationship.UNDIRECTED)
    public User creator;

    @Relationship(type = "VOTED_OPTION", direction = Relationship.UNDIRECTED)
    public Set<User> voters = new HashSet<User>();

    public Option(){

    }

    /**
     *
     * @param description
     * @param creator
     */
    public Option(String description, User creator) {
        this.description = description;
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Relationship(type = "OPTION_CREATOR", direction = Relationship.UNDIRECTED)
    public User getCreator() {
        return creator;
    }

    @Relationship(type = "OPTION_CREATOR", direction = Relationship.UNDIRECTED)
    public void setCreator(User creator){
        this.creator = creator;
    }




    @Relationship(type = "VOTED_OPTION", direction = Relationship.UNDIRECTED)
    public Set<User> getVoters() {
        return this.voters;
    }

    @Relationship(type = "VOTED_OPTION", direction = Relationship.UNDIRECTED)
    public void setVoters(Set<User> voters){
        this.voters = voters;
    }

    public void vote(User user){
        if(this.voters == null){
            this.voters = new HashSet<User>();
        }
        this.voters.add(user);
    }
}
