import groovy.json.JsonOutput
import jenkins.model.*

def call(body){
    def pipelineParams = [:]
    pipelineParams.testResults = []
    def failedStages = []
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    def Utils = new common.Utils()

    pipeline{
        
        agent any

        parameters {
            string(name: 'JENKINS_LIB_BRANCH', defaultValue: 'main', description: 'Jenkins branch for Jenkins pipeline')
        }

        stages {
            stage('parallel stages') {
                parallel {
                    stage("Parallel stage 1 mainstream") {
                        steps {
                            echo "-=- Parallel stage 1 -=-"
                        }
                    }

                    stage("Parallel stage 2") {
                        steps {
                            script {
                                def command = """
                                    echo "sdadas sdsa<software-rev>Hello</software-rev> sdad sda 192.168.0.1 dsfd 192.168.0.98" | perl -nle 'if (/192\\.168\\.0\\.(\\d{1,3})/ && \$1 >= 0 && \$1 <= 254) { print "192.168.0.\$1"; last; }' 
                                """

                                def command2 = """
                                    echo  | perl -0777 -ne 'if (/<software-rev>(.*?)<\\/software-rev>/s) { print "\$1\\n"; exit; }'
                                """
                                def result1 = sh(script: command, returnStdout: true).trim()
                                def result2 = sh(script: command2, returnStdout: true).trim()

                                println("RESULT1: " + result1)
                                println("RESULT2: " + result2)
                            }
                        }
                    }
                }
            }
        }

        post {
            always {
                echo "-=- Post job -=-"
            }
        }
    }
        
}