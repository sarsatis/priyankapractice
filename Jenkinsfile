def podTemplate = "podTemplate.yaml"

pipeline {
    agent {
        kubernetes {
            label "jenkins-${UUID.randomUUID().toString()}"
            yamlFile "$podTemplate"
        }
    }
    environment {
        NAME = "priyankapractice"
        VERSION = "${env.BUILD_ID}"
        IMAGE_REPO = "sarthaksatish"
        // ARGOCD_TOKEN = credentials('argocd-token')
        GITHUB_TOKEN = credentials('githubpat')
    }
    stages {
        stage('Unit Tests') {
            steps {
                echo 'Implement unit tests if applicable.'
                echo 'This stage is a sample placeholder'
            }
        }
        stage('gradle build') {
            steps {
                script {
                    container(name: 'gradle') {
                        sh 'gradle clean build'
                    }
                }
            }
        }
        stage('Build Image') {
            steps {
                script {
                    container(name: 'kaniko') {
                        sh """
                  printenv
                  /kaniko/executor --context `pwd` --destination ${IMAGE_REPO}/${NAME}:${VERSION}
                """
                    }
                }
            }
        }
        // stage('Helm deployments') {
        //   steps {
        //     script{
        //         container(name: 'helm3'){
        //             sh """
        //             helm list
        //             helm upgrade priyankapractice helm-charts-local --set image.tag=${VERSION}
        //             """
        //             }
        //           }
        //         }
        //       }     
        stage('Clone/Pull Repo') {
            steps {
                script {
                    sh 'git clone https://github.com/sarsatis/helm-charts'
                    sh 'ls -ltr'
                }
            }
        }
        stage('Commit & Push') {
            steps {
                script {
                    dir("helm-charts/manifests/${NAME}/") {
                        withCredentials([usernamePassword(
                            credentialsId: 'githubpat',
                            usernameVariable: 'username',
                            passwordVariable: 'password'
                        )]) {
                            encodedPassword = URLEncoder.encode("$password", 'UTF-8')
                            echo "sa ${encodedPassword}"
                            sh "git config --global user.email 'jenkins@ci.com'"
                            sh "git remote set-url origin https://${username}:${encodedPassword}@github.com/${username}/helm-charts.git"
                            sh 'sed -i "s#tag:.*#tag: ${VERSION}#g" values-dev.yaml'
                            sh "git checkout -b ${NAME}-${env.BUILD_ID}"
                            sh 'cat values-dev.yaml'
                            sh 'git add values-dev.yaml'
                            sh 'git commit -am "Updated image version for Build - $VERSION"'
                            echo 'push started'
                            sh "git push origin ${NAME}-${env.BUILD_ID}"
                        }
                        echo 'push complete'
                    }
                }
            }
        }
        stage('Raise PR') {
          steps {
             script {
                withCredentials([usernamePassword(credentialsId: 'githubpat',
                      usernameVariable: 'username',
                      passwordVariable: 'password')]){
                    encodedPassword = URLEncoder.encode("$password",'UTF-8')
                    echo 'In Pr'
                    container(name: 'python') {
                    sh "printenv"
                    sh "pip3 install -r requirements.txt"
                    sh "python3 createprandaddlabels.py"
                    }
                      }
                // sh "bash pr.sh"
            }
          }
        }
    }
}
