FROM public.ecr.aws/lambda/provided:al2

RUN bash < <(curl -s https://raw.githubusercontent.com/babashka/babashka/master/install)

COPY bootstrap ${LAMBDA_RUNTIME_DIR}
COPY bootstrap.clj ${LAMBDA_RUNTIME_DIR}

COPY foo.clj ${LAMBDA_TASK_ROOT}

CMD [ "foo.bar" ]