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
    implementation(project(":vbookstore.domain"))
    implementation(project(":commons"))
    implementation("org.springframework.boot:spring-boot-starter-data-rest:3.4.1")
    implementation("org.springframework.security:spring-security-core:6.4.2")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.3")
    testImplementation("com.h2database:h2:2.3.232")
}

tasks.test {
    useJUnitPlatform()
}