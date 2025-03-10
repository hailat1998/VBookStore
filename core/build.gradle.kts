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
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":security"))
    implementation("org.springframework.security:spring-security-core:6.4.2")
    implementation("org.springframework.data:spring-data-jpa:3.4.2")
    implementation("org.springframework.boot:spring-boot-starter-security:3.4.2")
    implementation("org.springframework.security:spring-security-core:6.4.2")
    implementation(project(":data"))
    implementation(project(":commons"))
    implementation("org.springframework.data:spring-data-jpa:3.4.2")
    implementation("org.modelmapper:modelmapper:3.2.2")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.4.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("org.springframework.data:spring-data-redis:3.4.2")
    implementation("com.stripe:stripe-java:28.3.0")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
}

tasks.test {
    useJUnitPlatform()
}