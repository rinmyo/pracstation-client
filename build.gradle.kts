import com.google.protobuf.gradle.*

plugins {
    java
    application
    id("io.freefair.lombok") version "5.3.0"
    id("org.openjfx.javafxplugin") version "0.0.9"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("com.google.protobuf") version "0.8.14"
}

group = "dev.glycine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/jerady/maven")
}

dependencies {
    implementation("org.apache.logging.log4j", "log4j-api", "2.14.0")
    implementation("org.apache.logging.log4j", "log4j-core", "2.14.0")
    implementation("com.jfoenix", "jfoenix", "9.0.10")
    implementation("com.google.code.gson", "gson", "2.8.6")
    implementation("com.google.protobuf", "protobuf-java", "3.14.0")
    implementation("io.grpc", "grpc-all", "1.34.0")
    implementation("javax.annotation","javax.annotation-api","1.3.2")
}

buildscript {
    repositories {
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.openjfx:javafx-plugin:0.0.9")
    }
}

apply(plugin = "org.openjfx.javafxplugin")

sourceSets{
    main {
        java {
            srcDir("build/generated/source/proto/main/grpc")
            srcDir("build/generated/source/proto/main/java")
        }
    }
}

javafx {
    version = "15"
    modules("javafx.controls", "javafx.fxml")
}

java {
    sourceCompatibility = JavaVersion.VERSION_15
}

application {
    mainClassName = "dev.glycine.pracstation.AppLauncher"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.34.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}