import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
	}
}

plugins {
	id("org.springframework.boot") version "3.4.1" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
	kotlin("jvm") version "2.1.0" apply false
	kotlin("plugin.spring") version "2.1.0" apply false
	kotlin("plugin.jpa") version "2.1.0" apply false
	java
}

allprojects {
	group = "vbookstore"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "java")
	apply(plugin = "java-library")

	configure<JavaPluginExtension> {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	dependencies {
		"implementation"("org.springframework.boot:spring-boot-starter")
		"implementation"("org.jetbrains.kotlin:kotlin-reflect")
		"implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		"testImplementation"("org.springframework.boot:spring-boot-starter-test")
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	// Configure Java compilation options
	tasks.withType<JavaCompile> {
		options.encoding = "UTF-8"
		options.compilerArgs.add("-Xlint:unchecked")
		options.compilerArgs.add("-Xlint:deprecation")
	}
}