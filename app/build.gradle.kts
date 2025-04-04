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
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":api"))
    implementation("org.springframework.data:spring-data-jpa:3.4.2")
    implementation("org.springframework.security:spring-security-core:6.4.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.3")
    testImplementation("com.h2database:h2:2.3.232")
    implementation("org.springframework.boot:spring-boot-starter:3.4.3")

}

tasks.test {
    useJUnitPlatform()
}