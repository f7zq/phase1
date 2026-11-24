plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.atlas.downloader"
    compileSdk = 35

    defaultConfig {
        applicationId = providers.gradleProperty("atlas.applicationId").get()
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0"

        // Only Arabic and English are supported.
        resourceConfigurations += listOf("ar", "en")
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"

            // Google's official sample / test AdMob IDs. Real ads are never loaded in debug builds.
            // (Verify these against Google's "Test ads" documentation before Phase 6.)
            manifestPlaceholders["admobAppId"] = "ca-app-pub-3940256099942544~3347511713"
            buildConfigField("String", "ADMOB_APP_ID", "\"ca-app-pub-3940256099942544~3347511713\"")
            buildConfigField("String", "AD_UNIT_BANNER", "\"ca-app-pub-3940256099942544/9214589741\"")
            buildConfigField("String", "AD_UNIT_INTERSTITIAL", "\"ca-app-pub-3940256099942544/1033173712\"")
            buildConfigField("String", "AD_UNIT_NATIVE", "\"ca-app-pub-3940256099942544/2247696110\"")
            buildConfigField("String", "AD_UNIT_APP_OPEN", "\"ca-app-pub-3940256099942544/9257395921\"")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            // Production AdMob IDs for ATLAS, exactly as provided. Do not change.
            manifestPlaceholders["admobAppId"] = "ca-app-pub-8202234833858378~7937122653"
            buildConfigField("String", "ADMOB_APP_ID", "\"ca-app-pub-8202234833858378~7937122653\"")
            buildConfigField("String", "AD_UNIT_BANNER", "\"ca-app-pub-8202234833858378/6292547690\"")
            buildConfigField("String", "AD_UNIT_INTERSTITIAL", "\"ca-app-pub-8202234833858378/2632825089\"")
            buildConfigField("String", "AD_UNIT_NATIVE", "\"ca-app-pub-8202234833858378/2214022689\"")
            buildConfigField("String", "AD_UNIT_APP_OPEN", "\"ca-app-pub-8202234833858378/5770124312\"")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.android)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)
}
