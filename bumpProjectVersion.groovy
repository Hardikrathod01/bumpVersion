def call() {
    library identifier: 'jenkins-library-util', changelog: false

    def currentVersion = getVersion()
    def releaseVersion = stripSuffix(currentVersion)
    def isSpa = JOB_BASE_NAME?.toLowerCase()?.startsWith('spa')
    def digitToIncrement = isSpa ? 'MAJOR' : 'MINOR'
    def suffix = (fileExists('package.json') && !isSpa) ? 'test-build' : 'SNAPSHOT'

    def newVersion = calculateVersion(currentVersion, digitToIncrement, suffix)

    writeVersion(newVersion)
    return newVersion
}
