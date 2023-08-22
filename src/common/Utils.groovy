package common

import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def getMainStreamJobInfo(job, component) {
    def build_info = [:]
    build_info["component"] = component
    build_info["description"] = job.getDescription()
    build_info["status"] = job.getResult()
    build_info["url"] = job.getAbsoluteUrl()
    build_info["id"] = job.getId()

    def buildVariables = job.getBuildVariables()
    build_info["failedStages"] = buildVariables.failedStages

    build_info["anotherENV"] = buildVariables.anotherEnv != null ? buildVariables.anotherEnv : ""

    return build_info
}