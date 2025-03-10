plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm")
}

group = "vbookstore"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.springframework:spring-web:6.2.2")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("com.peertopark.java:restclient:3.1.3")
    implementation(project(":domain"))
    implementation("org.springframework.data:spring-data-jpa:3.4.2")
    implementation(project(":commons"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")

}

tasks.test {
    useJUnitPlatform()
}