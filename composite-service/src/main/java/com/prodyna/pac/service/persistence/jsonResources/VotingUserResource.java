package com.prodyna.pac.service.persistence.jsonResources;

import com.prodyna.pac.model.Survey;
import com.prodyna.pac.model.VotingUser;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

/**
 * Created by bjoern on 06.06.16.
 */
public class VotingUserResource extends Resource<VotingUser> {

    public VotingUserResource() {
        super(new VotingUser());
    }

    public VotingUserResource(final VotingUser content, final Iterable<Link> links) {
        super(content, links);
    }

    public VotingUserResource(final VotingUser content, final Link... links) {
        super(content, links);
    }

}