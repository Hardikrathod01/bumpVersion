idef call(newVersion, buildDefType = null, source = null) {
    if (!newVersion) {
        error 'writeVersion: New version is required'
    }
    if (!buildDefType || !source) {
        def buildDef = getBuildDef()
        buildDefType = buildDef.type
        source = buildDef.source
    }
    switch (buildDefType) {
        case "GRADLE":
            sh "sed -Ei \"s/^version=.*/version=$newVersion/g\" gradle.properties"
            def newSource = readProperties file: 'gradle.properties'
            if (newSource.version != newVersion) {
                error("Could not write to gradle.properties - actual version=${newSource.version}, expected version=$newVersion, previous version=${source.version}.")
            }
            break
        case "UI":
            sh "sed -i -E \"s/\\\"version\\\": ?\\\".*\\\"/\\\"version\\\": \\\"$newVersion\\\"/g\" package.json"
            def newSource = readJSON file: 'package.json'
            if (newSource.version != newVersion) {
                error("Could not write to package.json - actual version=${newSource.version}, expected version=$newVersion, previous version=${source.version}.")
            }
            break
        case "MAVEN":
            def pom = source
            pom.setVersion(newVersion)
            writeMavenPom model: pom
    }
    if (logIsDebug()) {
        sh 'git diff'
    }
}
