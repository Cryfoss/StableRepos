plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
dependencies {
    implementation("org.projectlombok:lombok:1.18.32") // актуальная версия на июнь 2025
    annotationProcessor("org.projectlombok:lombok:1.18.32")
}