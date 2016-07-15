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

    @Relationship(type = "OPTION_VOTED_BY", direction = Relationship.INCOMING)
    private Set<Option> votes;

    @Relationship(type="SURVEY_CREATED_BY", direction = Relationship.INCOMING)
    private Set<Survey> createdSurveys;

    @Relationship(type = "OPTION_CREATED_BY", direction = Relationship.INCOMING)
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
        return "UserId=" + userId;
    }

    //Voted OPTIONS
    public void setVotes(Set<Option> votes){
        this.votes = votes;
    }

    public Set<Option> getVotes(){

        return this.votes;
    }


    //Created Surveys
    public Set<Survey> getCreatedSurveys(){

        return createdSurveys;
    }

    public void setCreatedSurveys(Set<Survey> createdSurveys){
        this.createdSurveys = createdSurveys;
    }

    
    
    //Created Options
    @Relationship(type = "OPTION_CREATED_BY", direction = Relationship.INCOMING)
    public Set<Option> getCreatedOptions(){
        return this.ceatedOptions;
    }

    @Relationship(type = "OPTION_CREATED_BY", direction = Relationship.INCOMING)
    public void setCreatedOptions(Set<Option> createdOptions){
        this.ceatedOptions = createdOptions;
    }

}
