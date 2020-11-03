pipeline{
    agent {label 'devtools-slave'}
    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '30', artifactNumToKeepStr: '30'))
    }
    parameters{
        string(defaultValue: 'https://demo.perfectomobile.com', description: 'Cloud URL', name: 'CLOUD_URL')
        string(name: 'SECURITY_TOKEN', defaultValue: 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzbFV4OFFBdjdVellIajd4YWstR0tTbE43UjFNSllDbC1TRVJiTlU1RFlFIn0.eyJqdGkiOiIxZGQ3YjE1ZC04YzNhLTRjNjAtYjU3YS1mNDA5MTIwODc4ZTIiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTcyNDI5NTIzLCJpc3MiOiJodHRwczovL2F1dGgucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL2RlbW8tcGVyZmVjdG9tb2JpbGUtY29tIiwiYXVkIjoib2ZmbGluZS10b2tlbi1nZW5lcmF0b3IiLCJzdWIiOiIxZDQ0YjA1NS03YjI2LTQ1MmMtOTNhYy05YWEyNDNlOWFlY2IiLCJ0eXAiOiJPZmZsaW5lIiwiYXpwIjoib2ZmbGluZS10b2tlbi1nZW5lcmF0b3IiLCJub25jZSI6ImU5YWIwZmY5LThiZmYtNDQxNS05Y2ZlLWJkMmYzMThmZWU1NSIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6ImVkZTMxNGUzLWEzOWQtNDQyMC04MTI2LWQwMjFmOTdlMTI0MiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fX0.d-GZ5AfMbdapNGaAVMKnRVKj9mTI0Fk7PYEY0IaM7PK_1B24jltCzY2W_9Gd7YocBSGtp4A0eNFs6WSv6xOyii2fb5lpp9Vd3IgdE_CZByUdD1GJqW4CR9TVjs_z26LeUdKMXKPsfYna6jXy_EtkOczDrdcGbnYy6rld7MhxTaC7A2jJ6MlgJak66sfLTVkmUze_iNI6HN6Sv3zyNlqXEO0HddutDe90yewR2c25CTxOLBBtii0Zjtgpp9cAKWnWMgOm9rLtOfC1yhAQRg1lwzY-qjIlpYpL6uMh7XBwWtUr-3Cen4K7GDzZ25P5gaDS9IPJhIh4tHVSX9eydSh1Qg')
    }
    stages{
        stage('Clean Up'){
            steps{
                cleanWs()
            }
        }
        stage('SCM CHECKOUT') {
            steps{
                git branch: 'master', url: 'git@github.com:PerfectoMobileDev/perfecto-connect-sample.git';
            }
        }
        stage('Download Perfecto Connect'){
            steps{
                sh "wget http://downloads.connect.perfectomobile.com/clients/Perfecto-Connect-linux.tar -O Perfecto-Connect-linux.tar"
                sh "tar -xf Perfecto-Connect-linux.tar"
            }
        }
        stage('Tests'){
            steps{
                retry(3){
                    script{
                        env.TUNNEL_ID=sh(script:"./perfectoconnect start -c ${params.CLOUD_URL} -s ${params.SECURITY_TOKEN} --logfile=log.log -lv)", returnStdout: true)
                        sh "./gradlew clean test -Pc=${params.CLOUD_URL} -Pt=${env.TUNNEL_ID} -Ps=${params.SECURITY_TOKEN} --rerun-tasks -i -Dtype=web"
                    }
                }
            }
        }
    }
    post{
        always{
            cleanWs()
        }
    }
}