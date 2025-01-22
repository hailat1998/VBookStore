plugins {
    id("java")
}

group = "vbookstore"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.data:spring-data-jpa:3.4.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    runtimeOnly("com.h2database:h2")
    implementation(project(":vbookstore.domain"))
// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-rest
    implementation("org.springframework.boot:spring-boot-starter-data-rest:3.4.1")
}

tasks.test {
    useJUnitPlatform()
}