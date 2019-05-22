pipeline {
  agent {
node('master')}

  tools {
        maven 'Maven'
    }
    environment {
        Parms = '-Dspring.profiles.active=test -Djavax.net.ssl.trustStore=/var/lib/jenkins/rds-ca-certs -Djavax.net.ssl.trustStorePassword=deloitte123 -Dtest.documentdb.user=demoapp -Dtest.documentdb.password=password321'
    }
    stages {
    stage ('Checking out the Source code') {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Bitbucket', url: 'https://10.0.4.136:8443/scm/sa4d/teammemberapp.git']]])
      }
   }
    stage ('Building the Code & Updating Coverage report') {
      steps {
        sh "mvn clean install jacoco:report -Dspring.profiles.active=test -Djavax.net.ssl.trustStore=/var/lib/jenkins/rds-ca-certs -Djavax.net.ssl.trustStorePassword=deloitte123 -Dtest.documentdb.user=demoapp -Dtest.documentdb.password=password321 sonar:sonar"
      }
    }
    stage ('Publishing Junit Reports on Jenkins') {
      steps {
      	junit allowEmptyResults: true,
	    testResults: 'target/surefire-reports/*.xml'
      }
	}
	stage ('Publishing Coverage Reports on Jenkins') {
      steps {
      	publishHTML([allowMissing: false,
      	alwaysLinkToLastBuild: true,
      	keepAll: true,
      	reportDir: 'target/site/jacoco',
      	reportFiles: 'index.html',
      	reportName: 'HTML Report', reportTitles: ''])
      }
	}
	stage ('Publishing Code Coverage Graph on Jenkins') {
      steps {
      	publishCoverage adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')],
      	sourceFileResolver: sourceFiles('NEVER_STORE')
      }
    }
    stage ('Uploading Artifact to Nexus repository') {
      steps {
      nexusArtifactUploader artifacts: [[artifactId: '${JOB_NAME}', classifier: '', file: 'target/devops-demo.war', type: 'war']], credentialsId: 'nexus', groupId: '${JOB_NAME}', nexusUrl: '10.0.56.187:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'maven-releases', version: '1.${BUILD_NUMBER}'
      }
    }
     stage ('Copying Application war file to Ansilbe Server') {
      steps {
        sh "scp /var/lib/jenkins/workspace/'${JOB_NAME}'/target/devops-demo.war ec2-user@10.0.55.78:/home/ec2-user/ansible/playbooks/roles/tomcat/files"
      }
    }
    stage ('Creating QA Environment in AWS with Ansible') {
      steps {
        sh "ssh ec2-user@10.0.55.78 ansible-playbook /home/ec2-user/ansible/EC2/ec2.yml --vault-password-file /home/ec2-user/ansible/EC2/vault_pass.txt"
      }
    }
    stage ('Deploying application code on QA Environment with ansible') {
      steps {
        sh "ssh ec2-user@10.0.55.78 ansible-playbook -i ansible/playbooks/hosts ansible/playbooks/apache-tomcat.yml"
      }
    }
    stage ('Updating the Record set in Route53 for QA Environment') {
      steps {
        sh "ssh ec2-user@10.0.55.78 python /home/ec2-user/Route53.py Name QALoadBalancer"
      }
    }

    stage ('Confirmation to Destroy QA Environment') {
      steps {
                input 'Terminate Dev-Servers?'
                milestone(1)
        }
    }

    stage ('Terminating QA Environment in AWS with Ansible') {
      steps {
        sh "ssh ec2-user@10.0.55.78 ansible-playbook /home/ec2-user/ansible/EC2/ec2terminate.yml --vault-password-file /home/ec2-user/ansible/EC2/vault_pass.txt"
      }
    }
    }
    post {
				success {
					sh '''
					#!/bin/bash
					curl -D- -u jira-admin:Deloitte@123 -X POST --data '{"fields":{"project":{"key": "JENKINS"},"summary": "New Change has been pushed Successfully!!!!! with build number : '${BUILD_NUMBER}'","description": "The latest successful build info can be found at : '${BUILD_URL}'","issuetype": {"name": "New Feature"}}}' -H "Content-Type: application/json" http://10.0.4.82:8080/rest/api/2/issue/

				'''
				}
				failure {
					sh '''

					curl -D- -u jira-admin:Deloitte@123 -X POST --data '{"fields":{"project":{"key": "JENKINS"},"summary": "The build has been failed!! with build number : '${BUILD_NUMBER}'","description": "The Build failure console can be found at : '${BUILD_URL}'","issuetype": {"name": "Bug"}}}' -H "Content-Type: application/json" http://10.0.4.82:8080/rest/api/2/issue/

				'''
			}
				unstable {
					sh '''

					curl -D- -u jira-admin:Deloitte@123 -X POST --data '{"fields":{"project":{"key": "JENKINS"},"summary": "The build is unstable with build number : '${BUILD_NUMBER}'","description": "The unstable Build console can be found at : '${BUILD_URL}'","issuetype": {"name": "Bug"}}}' -H "Content-Type: application/json" http://10.0.4.82:8080/rest/api/2/issue/

				'''
			}
		}
  }
