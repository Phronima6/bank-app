pipeline {
    agent any

    environment {
        IMAGE_TAG = '0.0.1-SNAPSHOT'
        SERVICES  = 'accounts blocker cash exchange exchange-generator front notifications transfer'
    }

    stages {
        stage('Сборка JAR всех микросервисов (fail-fast)') {
            steps {
                echo 'Шаг 1/3: Сборка JAR артефактов для всех сервисов'
                sh "chmod +x -R ${env.WORKSPACE}"
                script {
                    def services = env.SERVICES.split(' ')
                    for (svc in services) {
                        echo "Сборка JAR: ${svc}"
                        sh """
                            set -e
                            cd ${svc}
                            ./mvnw -q --no-transfer-progress clean package
                            cd -
                        """
                    }
                }
            }
        }

        stage('Сборка Docker-образов') {
            steps {
                echo 'Шаг 2/3: Сборка Docker-образов для всех сервисов'
                script {
                    def services = env.SERVICES.split(' ')
                    for (svc in services) {
                        def imageName = "${svc}-service:${env.IMAGE_TAG}"
                        echo "Сборка образа: ${imageName}"
                        sh "docker build -t ${imageName} ./${svc}"
                    }
                    echo 'Сборка образа: keycloak:pr'
                    sh 'docker build -t keycloak:pr ./keycloak'
                }
            }
        }

        stage('Загрузка образов в Minikube') {
            steps {
                echo 'Шаг 3/3: Загрузка всех образов в Minikube'
                script {
                    def services = env.SERVICES.split(' ')
                    for (svc in services) {
                        def imageName = "${svc}-service:${env.IMAGE_TAG}"
                        echo "minikube image load ${imageName}"
                        sh "minikube image load ${imageName}"
                    }
                    echo 'minikube image load keycloak:pr'
                    sh 'minikube image load keycloak:pr'
                }
            }
        }

        stage('Установка Helm') {
            steps {
                echo 'Деплой Helm чарта'
                sh """
                    helm dependency update ./k8s
                    helm install myapp ./k8s
                """
            }
        }
    }
}