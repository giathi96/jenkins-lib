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
                                println(currentBuild.number)
                                println(currentBuild.number.getClass().getName())
                            }
                        }
                    }
                }
                post {
                    always {
                        script {
                            println("POST OF PARALLEL")
                        }
                    }
                }
            }
        }

        post {
            always {
                script {
                    def map1 = [
                        total: 10,
                        pass: 7,
                        failed: 2,
                        skip: 1,
                    ]

                    def map2 = [
                        total: 13,
                        pass: 13,
                        failed: 0,
                        skip: 0,
                    ]

                    def sumMap = [:]

                    (map1.keySet() + map2.keySet()).each { key ->
                        sumMap[key] = (map1[key] ?: 0) + (map2[key] ?: 0)
                    }
                    println("NO FIELD: " + sumMap["NOTHIFNG"])
                    println(sumMap)
                }
            }
        }
    }
        
}