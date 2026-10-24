FROM jenkins/jenkins:lts-jdk17

USER root

# Instalación de Maven, Git y herramientas base
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        maven \
        git \
        curl \
        unzip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Verificación de herramientas instaladas
RUN java -version && mvn -version && git --version

# Regreso al usuario estándar de Jenkins
USER jenkins