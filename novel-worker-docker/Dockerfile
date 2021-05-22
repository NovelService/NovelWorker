FROM openjdk:11-jre-slim

RUN apt-get update -y
RUN apt-get install curl -y
RUN apt-get install sudo -y
RUN apt-get install libgl1-mesa-glx -y
RUN curl -fsSL https://deb.nodesource.com/setup_14.x | sudo -E bash -
RUN apt-get install nodejs  -y
RUN apt-get install yarn -y
RUN apt-get install wget -y
RUN apt-get install xdg-utils -y
RUN apt-get install xz-utils -y

# Install readability-cli
RUN npm i readability-cli@2.3.0 --global

# Install calibre for ebook-convert
RUN sudo -v && wget -nv -O- https://download.calibre-ebook.com/linux-installer.sh | sudo sh /dev/stdin version=5.18.0

COPY ./target/novel-worker.jar ./novel-worker.jar

CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "./novel-worker.jar"]