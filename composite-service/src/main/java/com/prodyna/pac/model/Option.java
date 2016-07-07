package com.prodyna.pac.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by bjoern on 07.03.16.
 */
public class Option {
    Long optionId;

    String description;

    public VotingUser creator;

    public Set<VotingUser> voters = new HashSet<VotingUser>();

    public Option(){

    }

    /**
     *
     * @param description
     * @param creator
     */

    public Option(String description, VotingUser creator) {
        this.description = description;
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public VotingUser getCreator() {
        return creator;
    }

    public void setCreator(VotingUser creator){
        this.creator = creator;
    }

    public Set<VotingUser> getVoters() {
        return this.voters;
    }

    public void setVoters(Set<VotingUser> voters){
        this.voters = voters;
    }

    public void vote(VotingUser user){
        if(this.voters == null){
            this.voters = new HashSet<VotingUser>();
        }
        this.voters.add(user);
    }
}
