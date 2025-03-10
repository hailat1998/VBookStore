plugins {
    id("java")
}

group = "vbookstore"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation(project(":domain"))
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.2")
    implementation("org.springframework.data:spring-data-commons:3.4.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.jayway.jsonpath:json-path:2.9.0")

}

tasks.test {
    useJUnitPlatform()
}