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
    implementation("org.springframework:spring-web:6.2.2")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:3.4.1")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(project(":commons"))
    implementation(project(":security"))
    implementation("org.springframework.data:spring-data-jpa:3.4.2")
    implementation("org.springframework.security:spring-security-core:6.4.2")
}

tasks.test {
    useJUnitPlatform()
}