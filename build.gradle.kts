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
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
tasks.test {
    useJUnitPlatform()
}