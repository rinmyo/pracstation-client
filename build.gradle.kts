plugins {
    java
    application
    id("io.freefair.lombok") version "5.0.0-rc6"
    id("com.github.johnrengelman.shadow").version("5.2.0")
}

group = "dev.glycine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/jerady/maven")
}

dependencies {
    testCompile("junit", "junit", "4.12")
    implementation("org.apache.logging.log4j", "log4j-api", "2.13.1")
    implementation("org.apache.logging.log4j", "log4j-core", "2.13.1")
    implementation("com.jfoenix", "jfoenix", "9.0.9")
    implementation("de.jensd", "fontawesomefx-commons", "11.0")
    implementation("org.mongodb", "mongodb-driver-sync", "4.0.1")
    implementation("com.google.code.gson", "gson", "2.8.6")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

configure<ApplicationPluginConvention> {
    mainClassName = "dev.glycine.pracstation.App"
}