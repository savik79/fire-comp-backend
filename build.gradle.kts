import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.4"
    java
}

group = "pl.jtrend.firecomp"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    // Core Web + REST
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Database: PostgreSQL + JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core:11.9.1")
    implementation("org.flywaydb:flyway-database-postgresql:11.9.1")
    runtimeOnly("org.postgresql:postgresql")

    // Validation (Bean Validation API)
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Spring Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    // JWT (JJWT)
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // MapStruct for DTO mapping
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    // OpenAPI/Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    implementation("org.springframework.boot:spring-boot-starter-mail")

    // DevTools for live reload (only in development)
    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")


    // Testcontainers PostgreSQL
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("com.h2database:h2")
}


tasks.named<BootRun>("bootRun") {
    systemProperty("spring.devtools.restart.enabled", "true")
    classpath = sourceSets.main.get().runtimeClasspath
    jvmArgs = listOf(
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
    )
}

tasks.test {
    useJUnitPlatform()
}
