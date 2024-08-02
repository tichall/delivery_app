pipeline {
    agent any
    stages {
        stage('Build') {
            when {
                expression {
                    // 현재 브랜치가 'main' 또는 'develop'일 때만 실행
                    return env.BRANCH_NAME == 'main' || env.BRANCH_NAME == 'develop'
                }
            }
            steps {
                echo "Building branch ${env.BRANCH_NAME}"
                // 빌드 작업 추가
            }
        }
    }
}
