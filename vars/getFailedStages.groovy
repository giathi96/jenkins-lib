import com.cloudbees.groovy.cps.NonCPS
import io.jenkins.blueocean.rest.impl.pipeline.PipelineNodeGraphVisitor
import io.jenkins.blueocean.rest.impl.pipeline.FlowNodeWrapper
import io.jenkins.blueocean.rest.model.BlueRun
import org.jenkinsci.plugins.workflow.job.WorkflowRun

@NonCPS
List<String> call() {
    def visitor = new PipelineNodeGraphVisitor((WorkflowRun) currentBuild.rawBuild)
    def failedStages = findFailedStages(visitor.getPipelineNodes())

    return failedStages.collect { it.displayName }
}

@NonCPS
List<FlowNodeWrapper> findFailedStages(List<FlowNodeWrapper> nodes) {
    return nodes.findAll {
        boolean isFailed = it.status.result == BlueRun.BlueRunResult.FAILURE
        boolean isRegularStage = it.type == FlowNodeWrapper.NodeType.STAGE

        boolean isBuiltParallelStage = it.type == FlowNodeWrapper.NodeType.PARALLEL && it.firstParent != null

        return isFailed && (isRegularStage || isBuiltParallelStage)
    }
}