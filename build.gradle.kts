import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import pl.allegro.tech.build.axion.release.domain.hooks.HookContext
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.jar.JarFile


buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.yaml:snakeyaml:2.0")
    }
}

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("pl.allegro.tech.build.axion-release") version "1.14.4"
    id("org.jlleitschuh.gradle.ktlint") version "11.3.2"
    id("io.ktor.plugin") version "2.3.0"
}

version = scmVersion.version

val group: String by project
val mcApiVersion: String by project
val repoRef: String by project
val ktorVersion: String by project

scmVersion {
    versionIncrementer("incrementMinorIfNotOnRelease", mapOf("releaseBranchPattern" to "release/.+"))

    hooks {
        // FIXME - workaround for Kotlin DSL issue https://github.com/allegro/axion-release-plugin/issues/500
        pre(
            "fileUpdate",
            mapOf(
                "file" to "CHANGELOG.md",
                "pattern" to KotlinClosure2<String, HookContext, String>({ v, _ ->
                    "\\[Unreleased\\]([\\s\\S]+?)\\n(?:^\\[Unreleased\\]: https:\\/\\/github\\.com\\/$repoRef\\/compare\\/[^\\n]*\$([\\s\\S]*))?\\z"
                }),
                "replacement" to KotlinClosure2<String, HookContext, String>({ v, c ->
                    """
                        \[Unreleased\]

                        ## \[$v\] - ${currentDateString()}$1
                        \[Unreleased\]: https:\/\/github\.com\/$repoRef\/compare\/v$v...HEAD
                        \[$v\]: https:\/\/github\.com\/$repoRef\/${if (c.previousVersion == v) "releases/tag/v$v" else "compare/v${c.previousVersion}...v$v"}${'$'}2
                    """.trimIndent()
                }),
            ),
        )

        pre("commit")
    }
}

fun currentDateString() = OffsetDateTime.now(ZoneOffset.UTC).toLocalDate().format(DateTimeFormatter.ISO_DATE)

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-websockets:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-websockets:$ktorVersion")

    implementation("com.google.code.gson:gson:2.10.1")

    compileOnly(group = "org.spigotmc", name = "spigot-api", version = "$mcApiVersion+")
}

val relocatingApi: Configuration by configurations.creating
val relocatingImplementation: Configuration by configurations.creating
configurations.api { extendsFrom(relocatingApi) }
configurations.implementation { extendsFrom(relocatingImplementation) }

dependencies {
    relocatingApi("io.ktor:ktor-server-core:$ktorVersion")
    relocatingApi("io.ktor:ktor-client-okhttp:$ktorVersion")
    relocatingApi("com.google.code.gson:gson:2.10.1")
}

tasks {
    wrapper {
        gradleVersion = "8.1.1"
        distributionType = Wrapper.DistributionType.ALL
    }

    processResources {
        val placeholders = mapOf(
            "version" to version,
            "apiVersion" to mcApiVersion,
            "kotlinVersion" to project.properties["kotlinVersion"],
        )

        filesMatching("plugin.yml") {
            expand(placeholders)
        }

        // create an "offline" copy/variant of the plugin.yml with `libraries` omitted
        doLast {
            val resourcesDir = sourceSets.main.get().output.resourcesDir
            val yamlDumpOptions =
                // make it pretty for the people
                DumperOptions().also {
                    it.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
                    it.isPrettyFlow = true
                }
            val yaml = Yaml(yamlDumpOptions)
            val pluginYml: Map<String, Any> = yaml.load(file("$resourcesDir/plugin.yml").inputStream())
            yaml.dump(pluginYml.filterKeys { it != "libraries" }, file("$resourcesDir/offline-plugin.yml").writer())
        }
    }

    jar {
        exclude("offline-plugin.yml")
    }

    val configureRelocation by creating {
        val relocatingConfigurations = listOf(relocatingApi, relocatingImplementation)

        val packages = buildSet {
            relocatingConfigurations.forEach { config ->
                config.files.forEach { jar ->
                    JarFile(jar).use { jarFile ->
                        jarFile.entries().asSequence().forEach { entry ->
                            if (entry.name.endsWith(".class") && entry.name != "module-info.class") {
                                val lastSlash = entry.name.lastIndexOf('/')
                                val pkg = entry.name.substring(0..lastSlash).replace('/', '.')
                                add(pkg)
                            }
                        }
                    }
                }
            }
        }
        val prefix = "${project.group}.${project.name.lowercase()}.libraries"
        shadowJar {
            packages.forEach {
                relocate(it, "$prefix.$it")
            }
        }
    }

    project.setProperty("mainClassName", "fyi.fyw.mc.pluginnonebot.PluginNonebot")
    // offline jar should be ready to go with all dependencies
    shadowJar {
        minimize()
        exclude("plugin.yml")
        rename("offline-plugin.yml", "plugin.yml")
    }

    build {
        dependsOn(shadowJar).dependsOn(configureRelocation)

        doLast {
            file("build/libs").listFiles()?.forEach {
                if (it.name.endsWith("-all.jar")) {
                    it.renameTo(file("build/libs/${project.name}-$version-all.jar"))
                }
            }
        }
    }
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    verbose.set(true)
    outputToConsole.set(true)
    enableExperimentalRules.set(true)
    disabledRules.set(
        setOf(
            "final-newline",
            "trailing-comma-on-call-site",
        ),
    )
}
