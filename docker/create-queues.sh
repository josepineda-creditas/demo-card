#!/usr/bin/env bash

echo "Create SQS queues"
awslocal sqs create-queue --queue-name payment-request-queue.fifo --attributes "FifoQueue=true";
awslocal sqs create-queue --queue-name payment-request-queue-deadletter.fifo --attributes "FifoQueue=true";
