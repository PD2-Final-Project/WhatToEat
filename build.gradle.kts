plugins {
    id("java")
}

group = "com.whattoeat"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // JSON API
    implementation ("org.json:json:20240303")
    // Google Map API
    implementation ("com.google.maps:google-maps-services:2.2.0")
    implementation ("org.slf4j:slf4j-simple:1.7.25")
    // Log4j
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
tasks.test {
    useJUnitPlatform()
}