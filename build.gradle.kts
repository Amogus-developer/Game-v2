plugins {
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("com.badlogicgames.gdx:gdx:1.11.0")
    implementation("com.badlogicgames.gdx:gdx-box2d:1.11.0")
    implementation("com.badlogicgames.gdx:gdx-freetype:1.11.0")

    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.11.0")
    implementation("com.badlogicgames.gdx:gdx-platform:1.11.0:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx-box2d-platform:1.11.0:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx-freetype-platform:1.11.0:natives-desktop")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}