# Serverless example of bb lambda

Simple example of babashka Clojure lambdas with dockerized runtime.

## Deploying

- install [serverless](serverless.com)
- run `sls deploy`

The first sls deployment takes a long time as the docker layers are created and pushed to ECR.
Subsequent deployments that only change the clj files are fast.

After sls deployment you can try out the lambdas:
```
% sls invoke --function hello -d '{"name": "Clojure world"}'
{
    "greeting": "Hello Clojure world!"
}

% sls invoke --function goodbye -d '{"name": "Clojure world", "day-phase": "afternoon"}'
{
    "farewell": "Goodbye Clojure world, have a pleasant afternoon"
}

# without name it errors
% sls invoke --function hello -d '{}'
{
    "errorMessage": "You must specify who to address",
    "errorType": "clojure.lang.ExceptionInfo",
    "stackTrace": [
    ...
```
