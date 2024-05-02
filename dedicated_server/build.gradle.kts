plugins {
    application
    alias(libs.plugins.kotlin.jvm)
}

group = "fr.agrouagrou"
version = "1.0"

dependencies {
    implementation(project(":common"))
    implementation(libs.clikt)
    implementation(libs.kotlinx.coroutines.core)

    api(libs.grpc.services)

    runtimeOnly(libs.grpc.netty)

    testImplementation(libs.kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
