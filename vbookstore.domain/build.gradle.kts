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
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    implementation("org.springframework.security:spring-security-core:6.4.2")
    implementation("org.hibernate:hibernate-core:6.6.5.Final")
    implementation("org.springframework.data:spring-data-commons:3.4.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.2")

}

tasks.test {
    useJUnitPlatform()
}