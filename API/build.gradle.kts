import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.20"
	kotlin("plugin.spring") version "1.5.20"
}

group = "com.seeds"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_18

repositories {
	mavenCentral()
}

dependencies {
	//Spring base dependencies
	implementation("org.springframework.boot:spring-boot-starter")

	//Sprint dependecy for API
	implementation ("org.springframework.boot:spring-boot-starter-web")

	//kotlin dependency
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	//Spring dependency for persistant API
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	//Spring dependency for postgres
	implementation("org.postgresql:postgresql")


	//Dependances pour le parsage de chaine d'elements en JSON
	implementation("com.google.code.gson:gson")


	implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
