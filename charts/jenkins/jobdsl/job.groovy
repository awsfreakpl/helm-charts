multibranchPipelineJob(jobProperties.githubRepo.repository) {
  displayName(jobProperties.githubRepo.repoOwner + "/" + jobProperties.githubRepo.repository)
  branchSources {
    github {
      credentialsId(jobProperties.githubRepo.credentialsId)
      repoOwner(jobProperties.githubRepo.repoOwner)
      repository(jobProperties.githubRepo.repository)
      id (jobProperties.githubRepo.repository)
      apiUri(jobProperties.githubRepo.apiUri)
      traits {
        localBranchTrait()
        sshCheckoutTrait {
          credentialsId(jobProperties.gitRepo.credentialsId)
        }
        headWildcardFilter {
          includes(jobProperties.branchDiscoveryIncludes)
          excludes(jobProperties.branchDiscoveryExcludes)
        }
      }
    }
  }
  orphanedItemStrategy {
    discardOldItems {
      daysToKeep(14)
    }
  }
  configure {
    def traits = it / sources / data / 'jenkins.branch.BranchSource' / source / traits
    traits << 'org.jenkinsci.plugins.github_branch_source.BranchDiscoveryTrait' {
      strategyId(3)
    }
  }
  configure {
    it / triggers / 'com.cloudbees.hudson.plugins.folder.computed.PeriodicFolderTrigger' {
      spec('*/5 * * * *')
      interval('300000')
    }
  }
}
