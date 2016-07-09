package com.prodyna.pac.domain;


import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

/**
 * Created by bjoern on 07.03.16.
 */
@NodeEntity
public class User {

    @GraphId
    Long graphId;

    private String userId;

    @Relationship(type = "VOTED_OPTION", direction = Relationship.UNDIRECTED)
    private Set<Option> votes;

    @Relationship(type="SURVEY_CREATOR", direction = Relationship.UNDIRECTED)
    private Set<Survey> createdSurveys;

    @Relationship(type = "OPTION_CREATOR", direction = Relationship.UNDIRECTED)
    private Set<Option> ceatedOptions;


    public User(){}

    public User(String userId){
        this.userId = userId;
    }

    public String getUserId(){

        return userId;
    }

    public Long getGraphId(){
        return graphId;
    }

    public String toString(){
        return "UserId=" + String.valueOf(userId);
    }

    //OPTIONS
    @Relationship(type = "VOTED_OPTION", direction = Relationship.UNDIRECTED)
    public void setVotes(Set<Option> votes){
        this.votes = votes;
    }

    @Relationship(type = "VOTED_OPTION", direction = Relationship.UNDIRECTED)
    public Set<Option> getVotes(){

        return this.votes;
    }


    //Created Surveys
    @Relationship(type="SURVEY_CREATOR", direction = Relationship.UNDIRECTED)
    public Set<Survey> getCreatedSurveys(){

        return createdSurveys;
    }

    public void setCreatedSurveys(Set<Survey> createdSurveys){
        this.createdSurveys = createdSurveys;
    }

    //Created Options
    @Relationship(type = "OPTION_CREATOR", direction = Relationship.UNDIRECTED)
    public Set<Option> getCreatedOptions(){
        return this.ceatedOptions;
    }

    @Relationship(type = "OPTION_CREATOR", direction = Relationship.UNDIRECTED)
    public void setCreatedOptions(Set<Option> createdOptions){
        this.ceatedOptions = createdOptions;
    }

}
