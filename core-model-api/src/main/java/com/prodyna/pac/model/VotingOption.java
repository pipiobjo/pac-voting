package com.prodyna.pac.model;

/**
 * Created by bjoern on 07.03.16.
 */
public class VotingOption {
    String description;
    VotingUser creator;
    VotingUser voter;

    public VotingOption(String description, VotingUser creator, VotingUser voter){
        this.description = description;
        this.creator = creator;
        this.voter = voter;
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

    public void setCreator(VotingUser creator) {
        creator = creator;
    }

    public VotingUser getVoter() {
        return voter;
    }

    public void setVoter(VotingUser voter) {
        voter = voter;
    }
}
