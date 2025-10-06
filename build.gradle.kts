plugins {
    id("java")
    //générer jar avec dépendences
    id ("com.github.johnrengelman.shadow") version "8.1.1"
    //générer un exe à partir du jar
    id("edu.sc.seis.launch4j") version "3.0.6"
}
tasks {
    shadowJar {
        archiveClassifier.set("") // Configure le JAR pour ne pas avoir de suffixe
        manifest {
            attributes["Main-Class"] = "com.astier.bts.Main" // Classe principale de l'application
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
group = "com.astier.bts"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //Gson
    implementation ("com.google.code.gson:gson:2.11.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}