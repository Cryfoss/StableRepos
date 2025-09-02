plugins {
    id("java")
    id ("io.qameta.allure") version "2.12.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
tasks.test {
    useJUnitPlatform()
    jvmArgs ("-Dfile.encoding=UTF-8")
}

dependencies {
    testImplementation ("org.assertj:assertj-core:3.26.0")

    testImplementation("org.instancio:instancio-junit:5.5.1")

    implementation("com.github.javafaker:javafaker:1.0.2")

    implementation("com.google.code.gson:gson:2.13.1")
    testImplementation("com.google.code.gson:gson:2.13.1")

    testImplementation("io.rest-assured:rest-assured:5.5.5")
    testImplementation("io.rest-assured:json-schema-validator:5.5.5")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    testImplementation("org.mockito:mockito-core:5.18.0")

    implementation("org.projectlombok:lombok:1.18.32") // актуальная версия на июнь 2025
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testCompileOnly("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")

    testImplementation ("io.qameta.allure:allure-junit5:2.27.0")
}


allure{
    version = "2.29.0"
    adapter.autoconfigure = true
    adapter.aspectjWeaver = true
}

