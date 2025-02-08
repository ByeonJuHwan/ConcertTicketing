plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "consert"
include("token-service")
include("token-service:token-api")
findProject(":token-service:token-api")?.name = "token-api"
include("token-service:token-worker")
findProject(":token-service:token-worker")?.name = "token-worker"
include("token-service:token-common")
findProject(":token-service:token-common")?.name = "token-common"
include("common")
