plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
tasks.test {
    useJUnitPlatform()
}
dependencies {
    implementation("org.projectlombok:lombok:1.18.32") // актуальная версия на июнь 2025
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testImplementation("io.rest-assured:rest-assured:5.5.5")
    testImplementation ("io.rest-assured:json-schema-validator:5.5.5")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation ("com.google.code.gson:gson:2.11.0")
    testImplementation("org.mockito:mockito-core:5.18.0")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testCompileOnly ("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")
}
