package com.prodyna.pac.model;

import java.util.Set;

/**
 * Created by bjoern on 07.03.16.
 */
public class Survey {
    private String description;
    private String title;
    private Set<VotingOption> options;
    private VotingUser creator;
    private String surveyId;

    public Survey(){}
    /**
     * @param description
     * @param title
     * @param options
     * @param creator
     */
    public Survey(String description, String title, Set<VotingOption> options, VotingUser creator) {
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

    public Set<VotingOption> getOptions() {
        return options;
    }

    public void setOptions(Set<VotingOption> options) {
        this.options = options;
    }

    public VotingUser getCreator() {
        return creator;
    }

    public void setCreator(VotingUser creator) {
        this.creator = creator;
    }

    public String getSurveyId() {
        return this.surveyId;
    }

}
