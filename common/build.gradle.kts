plugins {
    kotlin("jvm")
    kotlin("plugin.spring") // Spring 플러그인 추가
}

group = "dev"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Spring 의존성 추가
    implementation("org.springframework.boot:spring-boot-starter:3.2.2") // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.2") // Spring Web
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}