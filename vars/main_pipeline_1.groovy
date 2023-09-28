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
        
        agent none

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
                                def result = sh """
                                    echo "sdadas sdsa<software-rev>Hello</software-rev> sdad sda" | perl -0777 -ne 'if (/<software-rev>(.*?)<\\/software-rev>/s) { print "\$1\\n"; exit; }' 
                                """

                                println(result)
                            }
                        }
                    }
                }
            }
        }

        post {
            always {
                echo "-=- Post job -=-"
                script{                
                    def test = test_matcher()
                    println("PIPLINE RESULT ${test}")
                }
            }
        }
    }
        
}