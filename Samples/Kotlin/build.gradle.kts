plugins {
    kotlin("jvm") version "2.0.21" // Plugin para usar Kotlin con JVM
    kotlin("plugin.serialization") version "2.0.21" // Plugin de serialización
    application
}

java {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
}

kotlin {
    jvmToolchain(18) 
}

repositories {
    mavenCentral() // Asegúrate de tener repositorios configurados
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
}



