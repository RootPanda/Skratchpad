plugins {
    kotlin("jvm") version "1.3.72"
}

group = "ca.alexleung"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.javalin:javalin:3.8.0")
    implementation("org.slf4j:slf4j-simple:1.7.28")
    implementation("org.jetbrains.exposed", "exposed-core", "0.23.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.23.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.23.1")
    implementation("org.postgresql:postgresql:42.2.9")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("org.koin:koin-core:2.1.5")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

