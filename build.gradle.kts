plugins {
    java
    application
}

group = "dev.glycine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/jerady/maven")
}

dependencies {
    testCompile("junit", "junit", "4.12")
    implementation("com.jfoenix","jfoenix","9.0.9")
    compileOnly("org.projectlombok","lombok","1.18.12")
    annotationProcessor("org.projectlombok","lombok","1.18.12")
    testCompileOnly("org.projectlombok","lombok","1.18.12")
    testAnnotationProcessor("org.projectlombok","lombok","1.18.12")
    implementation("de.jensd","fontawesomefx-commons","11.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

configure<ApplicationPluginConvention> {
    mainClassName = "dev.glycine.pracstation.App"
}
