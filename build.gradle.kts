plugins {
    id("java")
    id ("application")
}

group = "com.ecwid.task"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.5.17")

    testImplementation(platform("org.junit:junit-bom:5.12.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    minHeapSize = "256m"
    maxHeapSize = "1024m"

    useJUnitPlatform()
}

application {
    mainClass = "com.ecwid.task.Main"
}

