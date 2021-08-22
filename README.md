# Babashka AWS Lambda custom runtime

This builds a custom runtime for creating AWS Lambda functions as babashka scripts.

## Building

To build the image you can run:
`bb build`

## Using

See `serverless-example` folder for an example.

Create a new Dockerfile that starts from the `bb-lambda` image
and copy Clojure source files to `${LAMBDA_TASK_ROOT}`.
The handler function is given as fully qualified clojure symbol (eg. `some.namespace/handler-fn-name`).
The bootstrap script will require the namespace and resolve the function name.

The handler function will receive two arguments: `event` and `context`.
The event is the JSON passed into the Lambda parsed as Clojure data (with keywordized keys) and
context contains the headers from API runtime invocation.

The handler must return a value that can be encoded as JSON.
If the handler throws an exception, an invocation error is signaled with
the exception message as the errorMessage in the response.

## Local testing

You can run the lambda locally in docker and test it with curl.

For example you can run the Clojure `merge` function as lambda like:
```
% docker run -p 9000:8080 -t bb-lambda clojure.core/merge
```

And test it with curl:
```
% curl -XPOST "http://localhost:9000/2015-03-31/functions/function/invocations" -d '{"hello": "world"}'
```
