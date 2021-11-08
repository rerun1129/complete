#!/usr/bin/env bash

# 쉬고 있는 profile 찾기 : real1과 2가 번갈아 가면서 쉰다.

function find_idle_profile()
{
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ]
    then
      CURRENT_PROFILE=real2
    else
      CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
      IDEL_PROFILE=real2
    else
      IDEL_PROFILE=real1
    fi

    echo "${IDEL_PROFILE}"

}

function find_idle_port()
{
    IDEL_PROFILE=$(find_idle_profile)

    if [ ${IDEL_PROFILE} == real1 ]
    then
      echo "8091"
    else
      echo "8092"
    fi
}