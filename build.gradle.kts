val group: String by project
val version: String by project
val repo: String by project

project.group = group
project.version = version

plugins {
    java
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.lombok)

    testImplementation(libs.bundles.junit)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.jdk.get())
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.processResources {
    val minecraftVersion =
        libs.versions.paper
            .get()
            .substringBefore("-")

    val commitHash = project.findProperty("commitHash") as String?

    val website =
        if (repo.isBlank()) {
            "https://joutak.ru"
        } else {
            if (commitHash.isNullOrBlank()) repo else "$repo/tree/$commitHash"
        }

    val props =
        mapOf(
            "NAME" to project.name,
            "VERSION" to project.version,
            "MINECRAFT_VERSION" to minecraftVersion,
            "WEBSITE" to website,
            "LOMBOK_VERSION" to libs.versions.lombok.get(),
        )

    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.jar {
    archiveFileName.set("${project.name}-${project.version}.jar")

    if (System.getenv("TEST_PLUGIN_BUILD") != null) {
        val serverPath = System.getenv("SERVER_PATH")
        if (serverPath != null) {
            destinationDirectory.set(file("$serverPath\\plugins"))
        } else {
            logger.warn("SERVER_PATH property is not set!")
        }
    }
}
