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
    // https://mvnrepository.com/artifact/jakarta.persistence/jakarta.persistence-api
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    // https://mvnrepository.com/artifact/org.springframework.security/spring-security-core
    implementation("org.springframework.security:spring-security-core:6.4.2")
    // https://mvnrepository.com/artifact/org.hibernate/hibernate-annotations
    implementation("org.hibernate:hibernate-annotations:3.5.6-Final")
// https://mvnrepository.com/artifact/org.springframework.data/spring-data-commons
    implementation("org.springframework.data:spring-data-commons:3.4.2")


}

tasks.test {
    useJUnitPlatform()
}