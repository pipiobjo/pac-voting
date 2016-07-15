package com.prodyna.pac.repo;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.prodyna.pac.domain.Survey;

/**
 * Created by bjoern on 07.03.16.
 */
public interface SurveyGraphRepository extends GraphRepository<Survey> {

    List<Survey> findByTitle(@Param("0") String title);

    List<Survey> findAll();

    //@Query("MATCH (s1:Survey) where s1.surveyId = {surveyId} return s1")
    Survey findBySurveyId(@Param("surveyId") String surveyId);


   //get the count of all votes for a survey
   //MATCH (s1:Survey)-[:VOTING_OPTIONS]-(opt:Option)-[votes:VOTED_OPTION]-(user:User) where s1.surveyId = '73593646-1209-44cc-a5e9-2a2e0b9a55d9' return count(votes)
   @Query("MATCH (s1:Survey)-[:VOTING_OPTIONS]-(opt:Option)-[votes:VOTED_OPTION]-(user:User) where s1.surveyId = {surveyId} return count(votes)")
   Long countVotesOnSurvey(@Param("surveyId") String surveyId);

   //get all voted surveys from a user
   //MATCH (s1:Survey)-[:VOTING_OPTIONS]-(opt:Option)-[:VOTED_OPTION]-(user:User) where user.userId='voter' return s1
   //@Query("MATCH (s1:Survey)-[:VOTING_OPTIONS]-(:Option)-[:VOTED_OPTION]-(user:User) WHERE user.userId='{votingUserId}' return s1")
    @Query("MATCH (s1:Survey)-[:VOTING_OPTIONS]-(:Option)-[:VOTED_OPTION]-(user:User {userId: {votingUserId}}) return s1")
    List<Survey> getAllSurveysForUser(@Param("votingUserId") String votingUserId);


    //Get all surveys created by user
    @Query("MATCH (n:Survey)-[:SURVEY_CREATOR]-(user:User {userId: {votingUserId}}) return n")
    List<Survey> findByCreator(@Param("votingUserId") String votingUserId);


}

