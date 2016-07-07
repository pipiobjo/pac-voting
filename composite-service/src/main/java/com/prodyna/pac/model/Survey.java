package com.prodyna.pac.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.Resource;

import java.util.*;

/**
 * Created by bjoern on 11.02.16.
 */
public class Survey {
    private String surveyId;
    private String description;
    private String title;

    public
    List<Option> options = new ArrayList<>();


    public VotingUser creator;

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
    public Survey(String description, String title, List<Option> options, VotingUser creator){
        this.surveyId = UUID.randomUUID().toString();
        this.description = description;
        this.title = title;
        this.options = options;
        this.creator = creator;
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

    public VotingUser getCreator() {
        return creator;
    }

    public void setCreator(VotingUser creator) {

        this.creator = creator;
    }

    public String getSurveyId(){

        return this.surveyId;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    /* public String toString() {
        String results = title + "'s Options include\n";
        if (options != null) {
            for (Option vop : options) {
                results += "\t- OptionId" + vop.optionId + "\n";
            }
        }
        results += "\t creator=" + this.creator.toString();
        return results;
    }*/
}