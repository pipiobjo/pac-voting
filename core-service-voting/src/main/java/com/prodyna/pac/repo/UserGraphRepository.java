package com.prodyna.pac.repo;

import com.prodyna.pac.domain.Survey;
import com.prodyna.pac.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by bjoern on 17.03.16.
 */
public interface UserGraphRepository extends GraphRepository<User> {


    List<User> findAll();

    User findByUserId(@Param("userId") String userId);

    //@Query("MATCH (user:VotingUser {userId:'{0}')-[votes:VOTED_OPTION]-(opt:Option)-[:VOTING_OPTIONS]-(s1:Survey) where user.userId = '{0}' return user")
    //Set<User> getAllVotedSurveysForUser(String votingUserId);


    @Query("MATCH (user:VotingUser {userId:'{0}'}) return user")
    Set<User> getAllVotedUsers(String votingUserId);



}
