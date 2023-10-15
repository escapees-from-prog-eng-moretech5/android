pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MORE.Tech VTB Map"
include(":app")
include(":core:network")
include(":features:auth")
include(":features:map")
include(":data:auth")
include(":data:map")
include(":core:database")
include(":core:location")
include(":core:activity")
