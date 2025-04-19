pipeline {
    agent any 
    environment {
        DOCKER_USERNAME = credentials('docker-username') // Jenkins credential ID
        DOCKER_PASSWORD = credentials('docker-password') // Jenkins credential ID
    }
    stages {
        stage('Pull') {
            steps {
                git branch: 'main', url: 'https://github.com/mayurmwagh/Newbackend.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                    echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                    docker build . -t mayurwagh/cdec-new-backend:latest 
                    docker push mayurwagh/cdec-new-backend:latest
                    docker rmi mayurwagh/cdec-new-backend:latest
                    kubectl apply -f ./deploy/
                '''
            }
        }
    }
}




// pipeline {
//     agent any 
//     stages {
//         stage ('pull'){
//             steps {
//                 git branch: 'main', url: 'https://github.com/mayurmwagh/Newbackend.git'
//             }
//         }
//         stage ('build'){
//             steps {
//                 sh 'mvn clean package'
//             }
//         }
//         stage ('deploy'){
//             steps {
//                 sh '''
//                     docker build . -t mayurwagh/cdec-new-backend:latest 
//                     docker push mayurwagh/cdec-new-backend:latest
//                     docker rmi mayurwagh/cdec-new-backend:latest
//                     kubectl apply -f ./deploy/
//                 '''
//             }
//         }
//     }
// }