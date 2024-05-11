pipeline {

    agent any

    parameters {
        string(name: "repoUrl", defaultValue: "https://gitlab.incarcloud.com/evcp/evcp-ic.git", description: "Git Repo Address")
        string(name: "repoBranchName", defaultValue: "dev", description: "Git Repo Branch")
        string(name: "repoCredentialsId", defaultValue: "incar-gitlab-pull", description: "Git Repo Auth")
        string(name: "dingTalkRobot", defaultValue: "dingtalk-robot-evcp", description: "Ding Talk Notify")
    }

    tools {
        jdk "jdk-17"
        gradle "gradle-8"
    }

    environment {
        HARBOR_NS = "evcp"
        HARBOR_HOST = "s1:5000"
        HARBOR_ACCOUNT = credentials("incar-harbor-push")
    }

    stages {
        //-------------------- BEGIN --------------------//
        stage("Preparation") {
            steps {
                echo "//---------  Preparation ----------//"
                git branch: params.repoBranchName, credentialsId: params.repoCredentialsId, url: params.repoUrl
            }
        }

        stage("Build") {
            steps {
                echo "//---------  Build ----------//"
                sh "gradle clean build"
                //sh "gradle clean build -x test"
                //sh "gradle clean build  -x test -x checkstyleMain"
            }
        }

        stage("Publish") {
            steps {
                echo "//---------  Publish ----------//"
                script {
                    def appVersion = sh (
                            script: "gradle properties -q | grep 'version:' | awk '{print \$2'}",
                            returnStdout: true
                        ).trim()
                    echo "Current version: ${appVersion}"

                    //sh "docker login ${params.harborHost} -u ${env.harborAuthName} -p ${env.harborAuthSecret}"
                    sh "echo ${env.HARBOR_ACCOUNT_PSW} | docker login ${env.HARBOR_HOST} -u ${env.HARBOR_ACCOUNT_USR} --password-stdin"
                    echo "Current Login: ${params.harborAuthName}"

                    def simpleNameList = ["ic-cloud-gateway", "ic-server", "ic-cpo", "ic-emsp", "ic-pay", "ic-iot/bootstrap"]
                    simpleNameList.each{simpleName ->
                        def appDir = simpleName
                        def appName = simpleName.replace("/", "-")
                        def dockerFilePath = env.WORKSPACE + "/${appDir}"
                        sh """
                            cd ${dockerFilePath}
                            docker build --build-arg APP_NAME=${appName} --build-arg APP_VERSION=${appVersion} -t ${env.HARBOR_HOST}/${env.HARBOR_NS}/${appName}:${appVersion} .
                            docker tag ${env.HARBOR_HOST}/${env.HARBOR_NS}/${appName}:${appVersion} ${env.HARBOR_HOST}/${env.HARBOR_NS}/${appName}:latest

                            docker push ${env.HARBOR_HOST}/${env.HARBOR_NS}/${appName}:${appVersion}
                            docker push ${env.HARBOR_HOST}/${env.HARBOR_NS}/${appName}:latest

                            docker rmi ${env.HARBOR_HOST}/${env.HARBOR_NS}/${appName}:${appVersion}
                            docker rmi ${env.HARBOR_HOST}/${env.HARBOR_NS}/${appName}:latest
                        """
                    }
                }
            }
        }
        //-------------------- END --------------------//
    }

    post {
        always {
            // Notification
            echo "//---------  Notification ----------//"
        }

        success  {
            echo "//---------  Pipeline Success ----------//"
            dingtalk (
                robot: params.dingTalkRobot,
                type: "ACTION_CARD",
                title: "你有新的消息，请注意查收！",
                text: [
                        "#### 构建通知",
                        "构建任务[${env.JOB_NAME}${env.BUILD_DISPLAY_NAME}](${env.BUILD_URL})已经**执行完成**！"
                ],
                singleTitle: "查看更多",
                singleUrl: "http://10.0.11.30:8066"
            )
        }

        failure {
            echo "//---------  Pipeline Failure ----------//"
            dingtalk (
                robot: params.dingTalkRobot,
                type: "ACTION_CARD",
                title: "你有新的消息，请注意查收！",
                text: [
                        "#### 构建通知",
                        "构建任务[${env.JOB_NAME}${env.BUILD_DISPLAY_NAME}](${env.BUILD_URL})最终**执行失败**！"
                ],
                singleTitle: "查看更多",
                singleUrl: "http://10.0.11.30:8066"
            )
        }
    }
}