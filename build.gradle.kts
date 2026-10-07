import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.AppendingTransformer
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension
import java.nio.charset.StandardCharsets
import java.nio.file.Files

buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.7.2")
        classpath(libs.flyway.postgres)
    }
}

plugins {
    id("java")
    id("idea")
    id("application")
    id("maven-publish")
    alias(libs.plugins.flyway)
    alias(libs.plugins.openapi.generator)
    alias(libs.plugins.shadow)
    alias(libs.plugins.dependencycheck)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(26))
}

configurations {
    create("spec") {
        extendsFrom(getByName("compileOnly"))
        isCanBeResolved = true
        description = "Workaround configuration to fetch OpenAPi specs from dependencies to custom dir"
    }
}

dependencies {
    add("spec", libs.antifraud.api)
    implementation(libs.bundles.docker) {
        exclude(group = "com.github.docker-java", module = "docker-java-transport-netty")
        exclude(group = "commons-logging", module = "commons-logging")
    }
    implementation(libs.json.schema.validator)
    implementation(libs.db.scheduler)
    implementation(libs.bundles.aws) {
        exclude(group = "software.amazon.awssdk", module = "netty-nio-client")
        exclude(group = "commons-logging", module = "commons-logging")
    }
    implementation(libs.bundles.vertx)
    implementation(libs.bundles.database)
    implementation(libs.bundles.logging)
    implementation(libs.jackson)
    implementation(platform(libs.jackson.bom))
    implementation(libs.simple.mail)
    implementation(libs.failsafe)
    implementation(libs.caffeine)
    implementation(libs.bucket4j.caffeine)
    implementation(libs.argon2)
    implementation(libs.vertx.auth.jwt)
    implementation(libs.vertx.web)
    implementation(libs.bundles.jjwt)
    implementation(libs.zip4j)
    implementation(libs.jsoup)
    implementation(libs.url.encoder)
    implementation(libs.avaje.config)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.dop)


    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

// .env is read through ProviderFactory.fileContents so that the configuration cache entry is
// invalidated when the file changes.
val dotEnvFile: RegularFile = layout.projectDirectory.file(".env")
val dotEnv: Map<String, String> = providers
    .fileContents(dotEnvFile)
    .asText
    .orElse("")
    .get()
    .lineSequence()
    .map { it.trim() }
    .filter { it.isNotEmpty() && !it.startsWith("#") && it.contains('=') }
    .associate { line ->
        val separator = line.indexOf('=')
        line.substring(0, separator).trim() to line.substring(separator + 1).trim().trim('"', '\'')
    }

val applicationTokens: Map<String, String> = mapOf(
    "legit_priv" to dotEnv["LEGIT_PRIV"].orEmpty(),
    "legit_pub" to dotEnv["LEGIT_PUB"].orEmpty(),
    "trust_secret" to dotEnv["TRUST_JWT_SECRET"].orEmpty(),
    "auth_secret" to dotEnv["AUTH_JWT_SECRET"].orEmpty(),
    "reset_secret" to dotEnv["RESET_JWT_SECRET"].orEmpty(),
    "mail_password" to dotEnv["MAIL_PASSWORD"].orEmpty(),
    "merchant_login" to dotEnv["ROBOKASSA_LOGIN"].orEmpty(),
    "shop_password_1" to dotEnv["ROBOKASSA_PASSWORD_1"].orEmpty()
)

val nvdApiKey: String = dotEnv["NVD_KEY"].orEmpty()
val apiSpecFile: File = layout.projectDirectory.file("provve-api.yaml").asFile

val jvmArgsReflectiveAccess = listOf(
    "--enable-native-access=ALL-UNNAMED",
    "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
    "--add-opens=java.base/java.nio=ALL-UNNAMED",
    "--add-opens=java.base/java.lang=ALL-UNNAMED",
    "--add-opens=java.base/java.util=ALL-UNNAMED",
    "--enable-final-field-mutation=ALL-UNNAMED",
    "-Dio.netty.tryReflectionSetAccessible=true"
)


openApiGenerate {
    generatorName.set("java-vertx-web")
    inputSpec.set(layout.projectDirectory.asFile.resolve("provve-api.yaml").invariantSeparatorsPath)
    outputDir.set(layout.projectDirectory.asFile.path)
    configOptions.set(
        mapOf(
            "invokerPackage" to "tech.provve.api",
            "apiPackage" to "tech.provve.api.generated.api",
            "interfaceOnly" to "true",
            "modelPackage" to "tech.provve.api.generated.dto"
        )
    )
    templateDir.set(layout.projectDirectory.asFile.resolve("src/main/resources/templates").invariantSeparatorsPath)
}
application {
    mainClass.set("tech.provve.api.Main")
    applicationDefaultJvmArgs = jvmArgsReflectiveAccess
}
dependencyCheck {
    nvd.apiKey.set(nvdApiKey)
}
tasks {
    val copyApiSpecs = register("copyApiSpecs") {
        val destinationDir = layout.buildDirectory.dir("specs")

        outputs.dir(destinationDir)

        configurations["spec"].resolve()
        configurations["spec"].resolvedConfiguration.resolvedArtifacts.forEach {
            if ("yaml" == it.extension) {
                val specContent = it.file.readText(StandardCharsets.UTF_8)
                val destination = destinationDir.get()
                    .file("${it.name}.${it.extension}")
                    .asFile
                Files.createDirectories(destination.parentFile.toPath())
                destination.writeText(specContent)
            }
        }
    }

    named("openApiGenerate") {
        dependsOn("copyApiSpecs")
    }

    withType<ProcessResources>().configureEach {
        duplicatesStrategy = DuplicatesStrategy.INHERIT

        // Copied into locals of this configuration action on purpose: a lambda that ends up stored
        // on a task must not capture the build script itself, otherwise the configuration cache
        // refuses to serialize it ("cannot serialize Gradle script object references").
        val tokens: Map<String, String> = applicationTokens
        val specs: TaskProvider<Task> = copyApiSpecs
        val envFile: File = dotEnvFile.asFile

        // the expanded values below come from .env, so a change of that file has to re-run the task
        inputs.file(envFile).optional()

        from(apiSpecFile)
        from(specs) {
            include("antifraud-api.yaml")
            into("build/specs")
        }

        filesMatching("application.yaml") {
            expand(tokens)
        }
    }

    withType<JavaExec>().configureEach {
        jvmArgs(jvmArgsReflectiveAccess)
        if (name == "run") {
            mainClass.set("tech.provve.api.Main")
            args = mutableListOf("run", "tech.provve.api.ApiServer")
        }
    }

    withType<ShadowJar>().configureEach {
        archiveVersion.set("0.0.1")
        archiveClassifier.set(null)
        //        minimize()

        exclude(
            "templates/*.mustache",
            "META-INF/maven/**",
            "META-INF/versions/**",
            "META-INF/native-image/**",
            "META-INF/*.RSA",
            "META-INF/*.DSA",
            "META-INF/*.SF",
            "**/LICENSE*",
            "**/NOTICE*",
            "META-INF/FastDoubleParser-LICENSE",
            "META-INF/DEPENDENCIES",
            "META-INF/FastDoubleParser-NOTICE",
            "META-INF/thirdparty-LICENSE",
            "*.txt",
            "INFO_*",
            "*.csv",
            "README",
            "google/**",
            "draft-*/**"
        )
        exclude { element -> element.name.endsWith(".properties") && element.name != "tinylog.properties" }

        transform<AppendingTransformer> {
            duplicatesStrategy =
                DuplicatesStrategy.INCLUDE // без обеих AppendingTransformer flyway не находит схемы. Но с ними пробелма дубрирования снова актуальна.
            resource = "org.flywaydb.core.extensibility.Plugin"
        }
        transform<AppendingTransformer> {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
            resource = "java.sql.Driver"
        }
        mergeServiceFiles() // без этого service-файлы заменяют друг друга

        manifest {
            attributes(
                "Main-Class" to "tech.provve.api.Main",
                "Main-Verticle" to "tech.provve.api.ApiServer"
            )
        }
    }

    withType<Test>().configureEach {
        useJUnitPlatform()
        jvmArgs(jvmArgsReflectiveAccess)
    }
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.isFork = true // compile in a separate and reusable process (fast)
    }
    idea {
        module.isDownloadJavadoc = true
    }
}
