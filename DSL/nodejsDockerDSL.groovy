job('Aplicacion Node.js Docker DSL') {
    description('Aplicación Node JS Docker DSL para el curso de Jenkins')
    scm {
        git('https://github.com/merq-rodriguez/nodejsapp', 'master') { node ->
            node / gitConfigName('merq-rodriguez')
            node / gitConfigEmail('merq.rodriguez@udla.edu.co')
        }
    }
    triggers {
        scm('H/7 * * * *')
    }
    wrappers {
        nodejs('AppNodejs')
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('stiivcode/app-nodejs')
            tag('${GIT_REVISION,length=7}')
            registryCredentials('docker-hub')
            forcePull(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
    publishers {
    slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
        }
    }
}
