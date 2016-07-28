#!/bin/bash


/home/bjoern/dev/programm/java/jdk1.8.0_71/bin/jvisualvm &


curl -X GET --header 'Accept: application/json' --header 'VOTING_EXECUTIVE_USER: bob' --header 'VOTING_EXEUCTING_AS_ROLE: ROLE_USERS' 'http://localhost:8280/surveys'

curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'VOTING_EXECUTIVE_USER: bob' --header 'VOTING_EXEUCTING_AS_ROLE: ROLE_USERS' -d '    "description": "Curl Survey",
    "title": "Curl Survey",
    "options": [
      {
        "optionId": "c090ed2d-6de9-4ba2-bc41-e1b9f5ba1e5d",
        "description": "Opt4",
        "creator": {
          "userId": "optionCreator"
        },
        "voters": []
      },
      {
        "optionId": "267d90ef-f247-4ab9-a94d-e5edfac42e02",
        "description": "Opt3",
        "creator": {
          "userId": "optionCreator"
        },
        "voters": [
          {
            "userId": "voter"
          },
          {
            "userId": "voter2"
          },
          {
            "userId": "voter3"
          }
        ]
      }
    ],
    "creator": {
      "userId": "surveyCreator"
    }
' 'http://localhost:8280/surveys'




##########

Do it Real
############

# -c concurrency
#  Number of multiple requests to perform at a time. Default is one request at a time.

# -n requests
# Number of requests to perform for the benchmarking session. The default is to just perform a single request which usually leads to non-representative benchmarking results.

ab -k -c 50 -n 2000 http://localhost:8080/api/surveys




