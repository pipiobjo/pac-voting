package com.prodyna.pac.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by bjoern on 11.02.16.
 */
@NodeEntity
public class Survey {
    private String description;
    private String title;
    private String surveyId;

    @GraphId
    private Long grapthId;


    @Relationship(type="SURVEY_HAS_VOTING_OPTIONS", direction = Relationship.OUTGOING)
    public
    Set<Option> options = new HashSet<Option>();


    @Relationship(type="SURVEY_CREATED_BY", direction = Relationship.OUTGOING)
    public User creator;

    /**
     *
     */
    public Survey(){

    }


    /**
     *
     * @param description
     * @param title
     * @param options
     * @param creator
     */
    public Survey(String description, String title, Set<Option> options, User creator){
        this.description = description;
        this.title = title;
        this.options = options;
        this.creator = creator;
        this.surveyId = UUID.randomUUID().toString();
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getSurveyId(){

        return this.surveyId;
    }
    public void setId(String surveyId) {
        this.surveyId = surveyId;
    }

    public Set<Option> getOptions() {

        return options;
    }

    public void setOptions(Set<Option> options) {

        this.options = options;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {

        this.creator = creator;
    }



    public String toString() {
        String results = title + "'s Options include\n";
        if (options != null) {
            for (Option vop : options) {
                results += "\t- OptionId" + vop.optionId + "\n";
            }
        }
        results += "\t creator=" + this.creator.toString();
        return results;
    }
}