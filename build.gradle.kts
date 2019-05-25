plugins {
    kotlin("jvm") version "1.3.31"
}

group = "com.github.shemnei"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))
    compile("org.json", "json", "20180813")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}