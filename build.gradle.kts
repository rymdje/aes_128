plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.0.13"

    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("edu.sc.seis.launch4j") version "3.0.6"
}

group = "com.astier.bts"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(23))
    }
}

javafx {
    version = "23"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        manifest {
            attributes["Main-Class"] = "com.astier.bts.Main"
        }
    }

    launch4j {
        outfile.set("AES.exe")
        mainClassName.set("com.astier.bts.Main")
        headerType.set("console")
        setJarTask(project.tasks.shadowJar.get())
        icon.set("${projectDir}/icons/aes.ico")
    }
}
