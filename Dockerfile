FROM public.ecr.aws/lambda/provided:al2

COPY bootstrap ${LAMBDA_RUNTIME_DIR}
COPY bootstrap.clj ${LAMBDA_RUNTIME_DIR}

COPY foo.clj ${LAMBDA_TASK_ROOT}

CMD [ "foo.bar" ]