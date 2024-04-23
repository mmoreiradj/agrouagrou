plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "fr.agrouagrou"
version = "1.0"

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
