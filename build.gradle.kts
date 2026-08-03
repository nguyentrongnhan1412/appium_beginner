plugins {
    id("java")
    id("io.qameta.allure") version "4.1.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("io.appium:java-client:10.1.1")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.testng:testng:7.12.0")
    implementation("io.qameta.allure:allure-testng:2.35.4")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.17")

    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
}

allure {
    version.set("2.35.0")
    adapter {
        resultsDir.set(layout.buildDirectory.dir("allure-results"))
    }
}

tasks.test {
    doFirst {
        delete(layout.buildDirectory.dir("allure-results"))
    }
    useTestNG {
        suites("src/test/resources/testng.xml")
    }

    // Forward -D overrides from the Gradle JVM into the TestNG worker JVM
    listOf(
        "env",
        "platform",
        "deviceName",
        "platformVersion",
        "udid",
        "app",
        "appPackage",
        "appActivity",
        "noReset",
        "fullReset",
        "test.account.username",
        "test.account.password"
    ).forEach { key ->
        System.getProperty(key)?.let { value -> systemProperty(key, value) }
    }

    // Generate HTML even when tests fail
    finalizedBy(tasks.allureReport)
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.allureReport {
    clean.set(true)
    doLast {
        val reportIndex = layout.buildDirectory
            .file("reports/allure-report/allureReport/index.html")
            .get()
            .asFile
        logger.lifecycle("Allure HTML report: ${reportIndex.toURI()}")
    }
}