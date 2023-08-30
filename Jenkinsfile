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
        stage('Prepare Build') {
            steps {
                sh 'mkdir -p layers/meta-estalor-distro/recipes-connectivity/wpa-supplicant/files/'
                sh 'cp /home/yocto/wpa_supplicant-nl80211-wlan0.conf layers/meta-estalor-distro/recipes-connectivity/wpa-supplicant/files/'
                sh 'cp /home/yocto/id_ed25519 layers/meta-estalor/recipes-connectivity/openssh/openssh/'
                sh 'cp /home/yocto/id_ed25519.pub layers/meta-estalor/recipes-connectivity/openssh/openssh/'
            }
        }
        stage('Test Homeassistant Arm64') {
            steps {
                sh 'kas build kas/estalor-homeassistant-arm64-a72.yml'
            }
        }
        stage('Test Homeassistant X86-64') {
            steps {
                sh 'kas build kas/estalor-homeassistant-x86-64.yml'
            }
        }
        stage('Build QemuArm64-a72') {
            steps {
                echo 'Building QemuArm64-a72 Image'
                sh 'kas build kas/estalor-qemuarm64-a72.yml'
            }
        }
        stage('Build Aarch64 Reterminal') {
            steps {
                echo 'Building Aarch64 Reterminal Image'
                sh 'kas build kas/estalor-aarch64-reterminal.yml'
            }
        }
        stage('Deploy on deploy-pi'){
            steps([$class: 'BapSshPromotionPublisherPlugin']) {
                sh 'cp build/tmp/deploy/images/reterminal/estalor-image-debug-reterminal.wic.bmap .'
                sh 'cp build/tmp/deploy/images/reterminal/estalor-image-debug-reterminal.wic.bz2 .'
                sh 'cp build/tmp/deploy/images/reterminal/estalor-reterminal-bundle-debug.raucb .'
                sshPublisher(
                    continueOnError: true, failOnError: false,
                    publishers: [
                        sshPublisherDesc(
                            configName: "Yocto-Deploy-Pi",
                            verbose: true,
                            transfers: [
                                sshTransfer(sourceFiles: "estalor-image-debug-reterminal.wic.bmap",),
                                sshTransfer(sourceFiles: "estalor-image-debug-reterminal.wic.bz2",),
                                sshTransfer(sourceFiles: "estalor-reterminal-bundle-debug.raucb",)
                            ]
                        )
                    ]
                )
            }
        }
        stage('Clean Cache'){
            steps {
                echo 'Cleaning sstate cache'
                sh './sources/poky/scripts/sstate-cache-management.sh --cache-dir=/cache/sstate --extra-layer=layers/meta-estalor-distro,layers/meta-estalor-distro,layers/meta-estalor -y -d -v'
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