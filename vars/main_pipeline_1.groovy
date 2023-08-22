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
                            echo "-=- Parallel stage 2 -=-"
                            // sh "cat sds.txt"
                        }
                    }
                }
            }

            stage('Deploy') {
                steps {
                    echo "-=- Deploy -=-"

                    script {
                        dowstream = build job: 'downstream-1',
                        parameters: [
                            string(name: 'JENKINS_LIB_BRANCH', value: JENKINS_LIB_BRANCH)
                        ],
                        propagate: false
                        
                        downstream_job_info = Utils.getMainStreamJobInfo(dowstream, 'DOWNSTREAM')
                        pipelineParams.testResults.add(downstream_job_info)
                        pipelineParams.buildID = dowstream.getId()
                        if(dowstream.getResult() != 'SUCCESS') {
                            error("DOWNSREAM FAILED")
                        }
                    }
                }
            }
        }

        post {
            always {
                echo "-=- Post job -=-"
                script{                
                    println("BUILD ID: " + pipelineParams.buildID)
                    def test_value = JsonOutput.toJson([test_data: [value_1: "1", value_2: env.testENVV]])
                    println("RESULT: " + test_value)
                    println("RESULT TYPE: " + test_value.getClass().getName())
                    
                }
            }
        }
    }
        
}