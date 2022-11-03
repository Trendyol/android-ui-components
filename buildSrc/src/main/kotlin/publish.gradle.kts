plugins {
    id("maven-publish")
}

afterEvaluate {
    publishing.publications {
        create<MavenPublication>("release") {
            from(components.getByName("release"))
        }
    }
}
