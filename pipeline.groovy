pipeline {
    agent any 
    stages {
        stage ('pull'){
            steps {
                git branch: 'main', url: 'https://github.com/mayurmwagh/Newbackend.git'
            }
        }
        stage ('build'){
            steps {
                sh 'mvn clean package'
            }
        }
        stage ('deploy'){
            steps {
                sh '''
                    docker build . -t mayurwagh/cdec-new-backend:latest 
                    docker push mayurwagh/cdec-new-backend:latest
                    docker rmi mayurwagh/cdec-new-backend:latest
                    kubectl apply -f ./deploy/
                '''
            }
        }
    }
}