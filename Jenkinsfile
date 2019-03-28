import java.util.regex.Matcher
import java.util.regex.Pattern

def SERVICE = "echo-service"
def IMAGE_NAME = "services/echo"
def DOCKER_BUILD_DIRECTORY = "${SERVICE}-server"
def DOCKERFILE = "./${SERVICE}-server/Dockerfile"
def DOCKER_REGISTRY = "337068080576.dkr.ecr.us-east-1.amazonaws.com"
def DOCKER_CREDENTIAL_ID = "AWS Jenkins"

def parseVersion(String version) {
    def FORMAT = "v(\\d+)\\.(\\d+)(?:\\.)?(\\d*)(\\.|-|\\+)?([0-9A-Za-z-.]*)?"
    def PATTERN = Pattern.compile(FORMAT)

    def matcher = PATTERN.matcher(version)
    if (!matcher.matches()) {
        throw new IllegalArgumentException(version.toString() + " formatted incorrectly")
    }

    final int major = Integer.valueOf(matcher.group(1))
    final int minor = Integer.valueOf(matcher.group(2))
    final int patch = Integer.valueOf(matcher.group(3))

    return [
            "major": major,
            "minor": minor,
            "patch": patch
    ]
}



properties([
        parameters([
                [$class: 'ChoiceParameter',
                 choiceType: 'PT_SINGLE_SELECT',
                 description: 'What type of release?',
                 filterLength: 1,
                 filterable: false,
                 name: 'version',
                 randomName: 'choice-parameter-5631314439613978',
                 script: [
                         $class: 'GroovyScript',
                         fallbackScript: [
                                 classpath: [],
                                 sandbox: true,
                                 script:
                                         """
                        return["Snapshot"]
                        """
                         ],
                         script: [
                                 classpath: [],
                                 sandbox: true,
                                 script:
                                         """
                    return ['Snapshot', 'Major', 'Minor', 'Patch']
                    """
                         ]
                 ]
                ],
                string(defaultValue: "", description: 'Version override', name: 'override')
        ])
])

BUILD_VERSION=""

pipeline {
    environment {
        ARTIFACTORY = credentials('ee31cd4e-7354-4387-830b-fea3c9e69e9c')
    }

    agent {
        kubernetes {
            label 'maven-builder'
            defaultContainer 'jnlp'
            yaml """
apiVersion: v1
kind: Pod
spec:
  idleMinutes: 10
  containers:
  - name: maven-builder
    image: ${DOCKER_REGISTRY}/jenkins-builders/maven
    command:
    - cat
    tty: true
  - name: docker
    image: docker:1.11
    command: 
    - cat
    tty: true
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
"""
        }
    }
    stages {

        stage('deep checkout') {
            steps {
                checkout([
                        $class: 'GitSCM',
                        branches: scm.branches,
                        doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
                        extensions: [[$class: 'CloneOption', noTags: false, shallow: false, depth: 0, reference: '']],
                        userRemoteConfigs: scm.userRemoteConfigs,
                ])
            }
        }

        stage('determine build version') {
            steps {
                script {
                    currentTag = sh(
                            script: 'git describe --tags --abbrev=0',
                            returnStdout: true
                    ).trim()

                    currentSha = sh(
                            script: 'git log --pretty=format:\'%h\' -n 1',
                            returnStdout: true
                    ).trim()

                    def parsedVersion = parseVersion(currentTag)
                    def updatedVersion

                    switch (params.version) {
                        case "Major":
                            updatedVersion = "v${parsedVersion.major + 1}.0.0"
                            break
                        case "Minor":
                            updatedVersion = "v${parsedVersion.major}.${parsedVersion.minor + 1}.0"
                            break
                        case "Patch":
                            updatedVersion = "v${parsedVersion.major}.${parsedVersion.minor}.${parsedVersion.patch + 1}"
                            break
                        case "Snapshot":
                            updatedVersion = "v${parsedVersion.major}.${parsedVersion.minor}.${parsedVersion.patch}-SNAPSHOT-${currentSha}"
                            break
                    }

                    echo "Requested version: ${params.version} Override: ${params.override} Current Tag: ${currentTag} Current SHA: ${currentSha} Updated Version: ${updatedVersion}"
                    BUILD_VERSION = updatedVersion
                }
            }
        }

        stage('build and test') {
            steps {
                container('maven-builder') {
                    echo "taco"
                    sh("""
                        sed "s/\\[ARTIFACTORY_USER\\]/$ARTIFACTORY_USR/g" /jenkins/settings.xml | sed "s/\\[ARTIFACTORY_PASSWORD\\]/$ARTIFACTORY_PSW/g" > jenkins-settings.xml
                    """)
                    sh 'mvn --settings jenkins-settings.xml clean install -Pshade'
                }
            }
        }

        stage('publish JARs') {
            steps {
                script {
                    echo "To Do!"
                }
            }
        }

        stage('publish python client') {
            steps {
                script {
                    echo "To Do!"
                }
            }
        }

        stage('update git tag') {
            when {
                expression {
                    return params.version != "Snapshot" || params.override != ""
                }
            }
            steps {
                script {
                    echo "To Do!"
                }
            }
        }

        stage('publish image to ECR') {
            steps {
                script {
                    container('docker') {
                        docker.withRegistry("https://${DOCKER_REGISTRY}", "ecr:us-east-1:${DOCKER_CREDENTIAL_ID}") {
                            def image = docker.build("${DOCKER_REGISTRY}/${IMAGE_NAME}:${BUILD_VERSION}", " -f ${DOCKERFILE} ${WORKSPACE}/${DOCKER_BUILD_DIRECTORY}")
                            echo "image created ${image.id}"
                            //push the image
                            image.push()
                        }

                    }
                }
            }
        }

        stage('success') {
            steps {
                script {
                    currentBuild.description = "${IMAGE_NAME} tagged successfully: ${BUILD_VERSION}"
                }
            }
        }
    }
}
