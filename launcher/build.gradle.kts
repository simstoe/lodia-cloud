plugins {
    application
}

group = "at.simstoe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":api"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainModule = "at.simstoe.cloud.launcher"
    mainClass = "at.simstoe.cloud.launcher.CloudLauncher"
}

tasks.test {
    useJUnitPlatform()
}
