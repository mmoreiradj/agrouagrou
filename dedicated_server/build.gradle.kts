plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.protobuf)
}

group = "fr.agrouagrou"
version = "1.0"

dependencies {
    protobuf(project(":protos"))

    implementation(project(":common"))
    implementation(libs.clikt)
    implementation(libs.kotlinx.coroutines.core)

    runtimeOnly(libs.grpc.netty)

    api(libs.grpc.protobuf)
    api(libs.grpc.services)
    api(libs.grpc.stub)
    api(libs.protobuf.kotlin)
    api(libs.grpc.kotlin.stub)

    testImplementation(libs.kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
}

protobuf {
    protoc {
        artifact = libs.protoc.asProvider().get().toString()
    }
    plugins {
        create("grpc") {
            artifact = libs.protoc.gen.grpc.java.get().toString()
        }
        create("grpckt") {
            artifact = libs.protoc.gen.grpc.kotlin.get().toString() + ":jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
                create("grpckt")
            }
            it.builtins {
                create("kotlin")
            }
        }
    }
}
