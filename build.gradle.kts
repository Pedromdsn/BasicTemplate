plugins {
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("me.bristermitten.pdm") version "0.0.32"
}

repositories {
    jcenter()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.elmakers.com/repository/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io")
}



dependencies {

    //Kotlin
    pdm(kotlin("stdlib-jdk8"))

    //Api de comandos
    pdm("com.github.SaiintBrisson.command-framework:bukkit:1.2.0")

    //JSON
    pdm("com.google.code.gson:gson:2.8.6")

    //Minecraft
    //compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT") //Original
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT") //NMS

    //plugins

}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime,kotlin.ExperimentalStdlibApi"
    }
    shadowJar {
        classifier = null

        destinationDir = if (File("C:\\Users\\pedro\\Desktop\\Internet Explorer\\Minecraft\\Server\\zArquivos\\1.8\\plugins").exists())
            File("C:\\Users\\pedro\\Desktop\\Internet Explorer\\Minecraft\\Server\\zArquivos\\1.8\\plugins")
        else if (File("C:\\Users\\pedro\\Desktop\\Internet Explorer\\Dev\\Minecraft\\1.8\\plugins").exists())
            File("C:\\Users\\pedro\\Desktop\\Internet Explorer\\Dev\\Minecraft\\1.8\\plugins")
        else
            File(".")
    }
    build {
        dependsOn("pdm", "shadowJar")
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(120, "seconds")
}

