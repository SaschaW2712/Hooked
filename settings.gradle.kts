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

rootProject.name = "Hooked"

include(":app")
include(":core")
include(":core:authentication")
include(":core:common")
include(":core:datastore")
include(":core:designsystem")
include(":core:model")
include(":core:network")
include(":feature:browse")
include(":feature:favorites")
include(":feature:onboarding")
