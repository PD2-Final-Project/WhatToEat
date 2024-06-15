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
    implementation("org.json:json:20240303")
    // Google Map API
    implementation("com.google.maps:google-maps-services:2.2.0")
    implementation("org.slf4j:slf4j-simple:1.7.25")
    // Log4j
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.whattoeat.Main"
    }

    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) {
            it
        } else {
            zipTree(it)
        }
    })
    from("src/main/resources/fonts/static/NotoSansTC-Bold.ttf")
    from("src/main/resources/log4j2.xml")
    archiveFileName.set("WhatToEat.jar")
}
