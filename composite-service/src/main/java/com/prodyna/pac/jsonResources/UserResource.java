package com.prodyna.pac.jsonResources;

import com.prodyna.pac.model.Survey;
import com.prodyna.pac.model.VotingUser;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

/**
 * Created by bjoern on 06.06.16.
 */
public class UserResource extends Resource<VotingUser> {

    public UserResource() {
        super(new VotingUser());
    }

    public UserResource(final VotingUser content, final Iterable<Link> links) {
        super(content, links);
    }

    public UserResource(final VotingUser content, final Link... links) {
        super(content, links);
    }

}