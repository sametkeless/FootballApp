pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "FootballApp"
include(":app")
include(":core:network")
include(":core:base")
include(":core:ui")
include(":feature:team:data")
include(":feature:team:domain")
include(":feature:team:presentation")
include(":feature:standings:data")
include(":feature:standings:domain")
include(":feature:standings:presentation")
