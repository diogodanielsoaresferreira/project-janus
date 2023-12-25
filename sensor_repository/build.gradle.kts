import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
}

group = "com.janus"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-amqp:3.0.4")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc:3.0.4")
	implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
	implementation("org.flywaydb:flyway-core:9.16.0")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")
	runtimeOnly("org.postgresql:postgresql:42.5.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
	testImplementation("org.springframework.amqp:spring-rabbit-test:3.0.2")
	testImplementation("org.springframework.boot:spring-boot-testcontainers:3.2.1")
	testImplementation("org.testcontainers:junit-jupiter:1.17.6")
	testImplementation("org.testcontainers:postgresql:1.17.6")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
