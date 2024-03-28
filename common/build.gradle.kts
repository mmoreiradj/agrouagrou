plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "fr.agrouagrou"
version = "1.0"

dependencies {
    testImplementation(libs.kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
