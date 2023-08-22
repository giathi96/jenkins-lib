import com.cloudbees.groovy.cps.NonCPS
import io.jenkins.blueocean.rest.impl.pipeline.PipelineNodeGraphVisitor
import io.jenkins.blueocean.rest.impl.pipeline.FlowNodeWrapper
import io.jenkins.blueocean.rest.model.BlueRun
import org.jenkinsci.plugins.workflow.job.WorkflowRun

@NonCPS
List<String> call() {
    def pipelineNodeGraphVisitor = new PipelineNodeGraphVisitor((WorkflowRun) currentBuild.rawBuild)
    def failedStages = getfailstages(pipelineNodeGraphVisitor.getPipelineNodes())

    return failedStages.collect { it.displayName }
}

@NonCPS
List<FlowNodeWrapper> getfailstages(List<FlowNodeWrapper> nodes) {
    return nodes.findAll {
        boolean isFail = it.status.result == BlueRun.BlueRunResult.FAILURE
        boolean isReguStage = it.type == FlowNodeWrapper.NodeType.STAGE

        boolean isParallelStage = it.type == FlowNodeWrapper.NodeType.PARALLEL && it.firstParent != null

        return isFail && (isReguStage || isParallelStage)
    }
}