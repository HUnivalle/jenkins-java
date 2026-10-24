pipeline {
    agent any

    options {
        // Mantiene solo los últimos 10 builds para optimizar espacio
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 15, unit: 'MINUTES')
        timestamps()
    }

    triggers {
        // Sondeo SCM cada 2 minutos (detecta commits enviados mediante git push)
        pollSCM('H/2 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                echo '==========================================================='
                echo ' [FASE 1] CHECKOUT: Descargando código fuente desde Git     '
                echo '==========================================================='
                checkout scm
                sh '''
                    echo "Rama actual: ${GIT_BRANCH:-'local'}"
                    echo "Último commit:"
                    git log -1 --pretty=format:"  Autor: %an (%ae)%n  Fecha: %ad%n  Mensaje: %s (%h)" || true
                '''
            }
        }

        stage('Compile') {
            steps {
                echo '==========================================================='
                echo ' [FASE 2] COMPILE: Compilando código Java con Maven        '
                echo '==========================================================='
                sh 'mvn clean compile'
            }
        }

        stage('Testing') {
            steps {
                echo '==========================================================='
                echo ' [FASE 3] TESTING: Ejecutando pruebas unitarias con JUnit   '
                echo '==========================================================='
                sh 'mvn test'
            }
            post {
                always {
                    // Publica resultados gráficos de pruebas JUnit en Jenkins
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo '==========================================================='
                echo ' [FASE 4] PACKAGE: Empaquetando la aplicación en JAR       '
                echo '==========================================================='
                sh 'mvn package -DskipTests'
                sh '''
                    echo "Artefacto generado en target/:"
                    ls -lh target/*.jar
                '''
            }
            post {
                success {
                    // Archiva el .jar para descarga directa desde la vista de Jenkins
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                    echo '>>> Artefacto JAR archivado exitosamente para distribución.'
                }
            }
        }
    }

    post {
        always {
            echo '==========================================================='
            echo ' Pipeline finalizado. Estado del build: ' + currentBuild.currentResult
            echo '==========================================================='
        }
        success {
            echo ' ¡CICLO CI EXITOSO! El código superó compilación, pruebas y empaquetado.'
        }
        failure {
            echo ' EL PIPELINE FALLÓ. Revisa la consola y los reportes de pruebas JUnit.'
        }
    }
}
