import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30"
}

group = "com.seeds"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	google()
	jcenter()
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

	implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.0")


	//Spring dependency for sending mail
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.sun.mail:javax.mail:1.6.2")

	//Spring dependency for postgres
	implementation("org.postgresql:postgresql")

	//Swagger pour la documentation de l'API
	implementation("io.springfox:springfox-swagger2:3.0.0")
	implementation ("io.springfox:springfox-boot-starter:3.0.0")
	implementation("io.springfox:springfox-swagger-ui:3.0.0")


	//Dependances pour le parsage de chaine d'elements en JSON
	implementation("com.google.code.gson:gson")


	//Librairies pour le numero de téléphone
	implementation ("com.googlecode.libphonenumber:libphonenumber:8.12.19")

	implementation("org.junit.platform:junit-platform-commons")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation("junit:junit:4.13")
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
