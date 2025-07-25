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

rootProject.name = "FinancesApp"
include(":app")
include(":core")
include(":core:ui")
include(":core:charts")
include(":feature")
include(":feature:settings")
include(":feature:categories")
include(":core:network")
include(":core:common")
include(":feature:account")
include(":feature:edit-account")
include(":feature:expenses")
include(":feature:incomes")
include(":core:database")
