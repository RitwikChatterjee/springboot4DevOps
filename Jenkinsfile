  pipeline {
    agent {
      node('master')}
    parameters {
          choice(name: 'DeploymentType', choices: 'FirstRun\nBlueGreen\nCanary\nDestroyProduction',description:'Select the Deployment Type')
      }
    tools {
        maven 'Maven'
      }
      environment {
          MAVEN_OPTS = '-Dspring.profiles.active=test -Djavax.net.ssl.trustStore=/var/lib/jenkins/rds-ca-certs -Djavax.net.ssl.trustStorePassword=deloitte123 -Dtest.documentdb.user=demoapp -Dtest.documentdb.password=password321'
      }
      stages {
      stage ('Checking out the Source code') {
      when {
        expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
      }
        steps {
          checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Bitbucket', url: 'https://10.0.4.136:8443/scm/sa4d/teammemberapp.git']]])
        }
     }

     stage ('Publishing to confluence') {
       when {
       expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
       }
       steps{
           publishConfluence attachArchivedArtifacts: true, editorList: [confluenceWritePage(generator: confluenceText('<br/>Job Name: ${JOB_NAME}<br/>Build ID: ${BUILD_ID}<br/>Build URL: ${BUILD_URL}<br/>Build Tag: ${BUILD_TAG}<br/>------------------------------------------'), markerToken: 'Logs')], fileSet: 'text.txt', pageName: 'DevOps Pipeline 1', replaceAttachments: false, siteName: '10.0.4.51', spaceName: 'PER'
           publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Checked out the code from the repositroy'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
       }   
       }
      stage ('Building the Code & Updating Coverage report') {
      when {
        expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
      }
        steps {
        withSonarQubeEnv('SonarQube') {
          sh "mvn clean install jacoco:report -Dspring.profiles.active=test -Djavax.net.ssl.trustStore=/var/lib/jenkins/rds-ca-certs -Djavax.net.ssl.trustStorePassword=deloitte123 -Dtest.documentdb.user=demoapp -Dtest.documentdb.password=password321 sonar:sonar"
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Updated Code Coverage Report'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
        }
        }
        }
        stage ('Publishing Junit Reports on Jenkins') {
        when {
          expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
        }
        steps {
            junit allowEmptyResults: true,
            testResults: 'target/surefire-reports/*.xml'
            publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Published Junit Reports on Jenkins'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
            
          }
      }
  	   stage ('Publishing Coverage Reports on Jenkins') {
       when {
         expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
       }
        steps {
        	publishHTML([allowMissing: false,
        	alwaysLinkToLastBuild: true,
        	keepAll: true,
        	reportDir: 'target/site/jacoco',
        	reportFiles: 'index.html',
        	reportName: 'HTML Report', reportTitles: ''])
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Published Coverage Reports on Jenkins'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
          
        }
  	}
  	   stage ('Publishing Code Coverage Graph on Jenkins') {
       when {
         expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
       }
          steps {
        	publishCoverage adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')],
        	sourceFileResolver: sourceFiles('NEVER_STORE')
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Published Code Coverage Graph on Jenkins'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
        }
      }
      stage("Quality Gate on SonarQube") {
      when {
        expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
      }
              steps {        
                timeout(time: 1, unit: 'HOURS') {
                waitForQualityGate abortPipeline: true
                publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Quality Gate on SonarQube'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
  			}
          }
      }
      
      stage ('Uploading Artifact to Nexus repository') {
      when {
        expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
      }
        steps {
        nexusArtifactUploader artifacts: [[artifactId: '${JOB_NAME}', classifier: '', file: 'target/devops-demo.war', type: 'war']], credentialsId: 'nexus', groupId: '${JOB_NAME}', nexusUrl: '10.0.56.187:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'maven-releases', version: '1.${BUILD_NUMBER}'
        publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Uploaded Artifact to Nexus repository'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
        }
      }
       stage ('Copying Application war file to Ansilbe Server') {
       when {
         expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
       }
        steps {
          sh "scp /var/lib/jenkins/workspace/'${JOB_NAME}'/target/devops-demo.war ec2-user@10.0.55.78:/home/ec2-user/ansible/QA/playbooks/roles/tomcat/files"
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Copied Application war file to Ansilbe Server'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
        }
      }
      stage ('Creating QA Environment in AWS with Ansible') {
      when {
        expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
      }
        steps {
          sh "ssh ec2-user@10.0.55.78 ansible-playbook /home/ec2-user/ansible/QA/EC2/ec2.yml --vault-password-file /home/ec2-user/ansible/QA/EC2/vault_pass.txt"
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Created QA Environment in AWS with Ansible'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
        }
      }
      stage ('Deploying application code on QA Environment with ansible') {
      when {
        expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
      }
        steps {
          sh "ssh ec2-user@10.0.55.78 ansible-playbook -i /home/ec2-user/ansible/QA/playbooks/hosts /home/ec2-user/ansible/QA/playbooks/apache-tomcat.yml"
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Deployed application code on QA Environment with ansible'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
        }
      }
      stage ('Updating the Record set in Route53 for QA Environment') {
      when {
        expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
      }
        steps {
          sh "ssh ec2-user@10.0.55.78 python /home/ec2-user/recordset.py Name QALoadBalancer qa-app"
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Updated the Record set in Route53 for QA Environment'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
        }
      }
      stage ('Confirmation to Destroy QA Environment and Create Production Enviornment') {
      when {
        expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
      }
        steps {
                  input 'Terminate QA Environment and Provision Production Environment?'
                  milestone(1)
                  publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Waited for human on confirmation to Destroy QA Environment'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
          }
      }

      stage ('Terminating QA Environment in AWS with Ansible') {
      when {
        expression { params.DeploymentType == 'FirstRun' || params.DeploymentType == 'BlueGreen' || params.DeploymentType == 'Canary'}
      }
        steps {
          sh "ssh ec2-user@10.0.55.78 ansible-playbook /home/ec2-user/ansible/QA/EC2/ec2terminate.yml --vault-password-file /home/ec2-user/ansible/QA/EC2/vault_pass.txt"
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Terminated QA Environment in AWS with Ansible'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
        }
      }
      stage ('Creating Production Environment in AWS with Ansible') {
        when {
              expression { params.DeploymentType == 'FirstRun'}
              }
        steps {
          sh "ssh ec2-user@10.0.55.78 ansible-playbook /home/ec2-user/ansible/PROD/EC2/ec2.yml --vault-password-file /home/ec2-user/ansible/PROD/EC2/vault_pass.txt"
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Created Production Environment in AWS with Ansible'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
        }
      }
      stage('Copying Application war file to Ansilbe Server for Production Deployment') {     
          when {
              expression { params.DeploymentType == 'FirstRun' }
              }
          steps {
  		    sh "scp /var/lib/jenkins/workspace/'${JOB_NAME}'/target/devops-demo.war ec2-user@10.0.55.78:/home/ec2-user/ansible/PROD/playbooks/roles/tomcat/files"
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Copied Application war file to Ansilbe Server for Production Deployment'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
            }
          }
      stage ('Deploying application on Production Environment with ansible') {
        when {
              expression { params.DeploymentType == 'FirstRun' }
              }
        steps {
          sh "ssh ec2-user@10.0.55.78 ansible-playbook -i /home/ec2-user/ansible/PROD/playbooks/hosts /home/ec2-user/ansible/PROD/playbooks/apache-tomcat.yml"
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Deployed application on Production Environment with ansible'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
        }
      }
      stage ('Updating the Record set in Route53 for Production Environment') {
        when {
              expression { params.DeploymentType == 'FirstRun' }
              }
        steps {
          sh "ssh ec2-user@10.0.55.78 python /home/ec2-user/recordset.py Name PRODLoadBalancer prod-app"
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Updated the Record set in Route53 for Production Environment'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
        }
      }
      stage ('Terminating Production Environment in AWS with Ansible') {
      when {
        expression { params.DeploymentType == 'DestroyProduction'}
      }
        steps {
          sh "ssh ec2-user@10.0.55.78 ansible-playbook /home/ec2-user/ansible/QA/EC2/ec2terminate.yml --vault-password-file /home/ec2-user/ansible/QA/EC2/vault_pass.txt"
          publishConfluence attachArchivedArtifacts: true, editorList: [confluenceAppendPage(confluenceText('<br/><br/>Terminated Production Environment in AWS with Ansible'))], labels: 'Custom Label', pageName: 'DevOps Pipeline 1', replaceAttachments: true, siteName: '10.0.4.51', spaceName: 'per'
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
