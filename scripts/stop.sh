#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh # 이 파일에 profile.sh import 함

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT에서 구동 중인  애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID}]
then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  KILL -15 ${IDLE_PID}
  slepp 5
fi