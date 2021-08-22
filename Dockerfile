FROM public.ecr.aws/lambda/provided:al2

RUN yum -y install tar gzip
RUN curl -o bb.tar.gz -sL https://github.com/babashka/babashka/releases/download/v0.5.1/babashka-0.5.1-linux-amd64-static.tar.gz
RUN tar zxf bb.tar.gz
RUN rm bb.tar.gz
RUN mv bb ${LAMBDA_RUNTIME_DIR}

COPY bootstrap ${LAMBDA_RUNTIME_DIR}
COPY bootstrap.clj ${LAMBDA_RUNTIME_DIR}

COPY foo.clj ${LAMBDA_TASK_ROOT}
