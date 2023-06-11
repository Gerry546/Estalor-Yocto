void setBuildStatus(String message, String state) {
  step([
      $class: "GitHubCommitStatusSetter",
      reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/Gerry546/Estalor-Yocto"],
      contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
      errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
      statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
  ]);
}

pipeline {
    agent { label 'estalor'}

    stages {
        stage('Build QemuArm64-a53') {
            steps {
                echo 'Building QemuArm64-a53 Image'
                sh 'python3 -m kas build kas/estalor-qemuarm64-a53.yml'
            }
        }
        stage('Build Aarch64 Reterminal') {
            steps {
                echo 'Building Aarch64 Reterminal Image'
                sh 'python3 -m kas build kas/estalor-aarch64-reterminal.yml'
            }
        }
        stage('Cleanup'){
            steps {
                echo 'Cleanup up build folder'
                sh 'rm -rf build'
            }
        }
    }
    post {
        success {
            setBuildStatus("Build succeeded", "SUCCESS");
        }
        failure {
            setBuildStatus("Build failed", "FAILURE");
        }
    }
}