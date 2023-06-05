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
        stage('Build') {
            steps {
                echo 'Building QemuArm53 Image'
                sh 'python3 -m kas build kas/estalor-qemuarm-a53.yml'
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