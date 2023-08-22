import groovy.json.JsonOutput

def call(body){
    def pipelineParams = [:]
    pipelineParams.testResults = []
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegaet = pipelineParams

    body()

    pipeline{
        
        agent none

        parameters {
            string(name: 'JENKINS_LIB_BRANCH', defaultValue: 'main', description: 'Jenkins branch for Jenkins pipeline')
        }

        stages {
            stage('Downstream Parallel stages') {
                parallel {
                    stage("Downstream Parallel stage 1") {
                        steps {
                            echo "-=- Downstream Parallel stage 1 -=-"
                            
                        }
                        post {
                            always {
                                script {
                                    println("STAGE IN STAGE")
                                }
                            }
                        }
                    }

                    stage("Downstream Parallel stage 2") {
                        steps {
                            echo "-=- Downstream Parallel stage 2 -=-"
                            sh "cat sdasd.txt"
                            
                        }
                    }
                }
            }

        }

        post {
            always {
                echo "-=- Post job -=-"

                script {               
                    def failedStages = getFailedStages().join(", ")
                    env.failedStages = failedStages
                    env.anotherEnv = "AFTER"
                    println("Failed stage: " + failedStages)
                }
            }
        }
    }
        
}