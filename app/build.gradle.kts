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
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.4.1")
    implementation("org.springframework.boot:spring-boot-devtools:3.4.1")
    implementation(project(":vbookstore.domain"))
    implementation(project(":vbookstore.data"))
    implementation(project(":vbookstore.api"))
    implementation(project(":vbookstore.restclient"))
    implementation("org.springframework.data:spring-data-jpa:3.4.2")
}

tasks.test {
    useJUnitPlatform()
}