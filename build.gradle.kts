import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
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
    id("org.jlleitschuh.gradle.ktlint") version "11.3.2"
    id("io.ktor.plugin") version "2.3.0"
}

val group: String by project
val mcApiVersion: String by project
val ktorVersion: String by project
val version: String by project

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
    implementation(kotlin("stdlib"))
//    implementation("io.ktor:ktor-server-core:$ktorVersion")
//    implementation("io.ktor:ktor-server-netty:$ktorVersion")
//    implementation("io.ktor:ktor-server-websockets:$ktorVersion")
//    implementation("io.ktor:ktor-client-core:$ktorVersion")
//    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
//    implementation("io.ktor:ktor-client-websockets:$ktorVersion")
    implementation("org.java-websocket:Java-WebSocket:1.5.3")

    implementation("com.google.code.gson:gson:2.10.1")
//    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")

    compileOnly(group = "org.spigotmc", name = "spigot-api", version = "$mcApiVersion+")
}

val relocatingApi: Configuration by configurations.creating
val relocatingImplementation: Configuration by configurations.creating
configurations.api { extendsFrom(relocatingApi) }
configurations.implementation { extendsFrom(relocatingImplementation) }

dependencies {
//    relocatingApi("io.ktor:ktor-server-core:$ktorVersion")
//    relocatingApi("io.ktor:ktor-client-okhttp:$ktorVersion")
//    relocatingApi(kotlin("stdlib"))
    relocatingApi("org.java-websocket:Java-WebSocket:1.5.3")
    relocatingApi("com.google.code.gson:gson:2.10.1")
//    relocatingApi("com.fasterxml.jackson.core:jackson-databind:2.14.2")
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
        val prefix = "${project.group}.libraries"
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
        dependencies {
            exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib"))
        }
        rename("offline-plugin.yml", "plugin.yml")
    }

    build {
        dependsOn(shadowJar).dependsOn(configureRelocation)

        doLast {
            file("build/libs").listFiles()?.forEach {
                if (it.name.endsWith("all.jar")) {
                    it.renameTo(file("build/libs/${project.name}-$version-all.jar"))
                }
            }
        }
    }

    register("devDebug") {
        dependsOn(build)

        doLast {
            copy {
                from("build/libs/${project.name}-$version-all.jar")
                rename("${project.name}-$version-all.jar", "${project.name}.jar")
                into("../../Bukkit-plugin/server/plugins/")
            }
        }
    }
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    version.set("0.47.1")
    verbose.set(true)
    outputToConsole.set(true)
    enableExperimentalRules.set(false)
    disabledRules.set(
        setOf(
            "final-newline",
            "trailing-comma-on-call-site",
            "trailing-comma-on-declaration-site",
            "no-consecutive-blank-lines",
            "no-wildcard-imports",
        ),
    )
}
