#!/usr/bin/env bash
docker rm $(docker ps -a -q) -f
docker system prune -f
docker system prune -f -a
docker container prune -f
docker volume prune -f
docker volume prune -f -a
docker network prune -f
docker image prune -f
#sudo chmod 777 -R docker-local
sudo rm -rf docker-local
sudo systemctl restart docker
sudo chmod 777 -R /var/lib/docker/volumes