object Versions {
    const val kotlin = "1.4.10"
    const val hilt = "2.28-alpha"
    const val hiltx = "1.0.0-alpha02"
    const val lifecycle = "2.2.0"
    const val recyclerview = "1.1.0"
    const val material = "1.2.0"
    const val ConstraintLayout = "1.1.3"
    const val legacySupport = "1.0.0"
    const val navigation = "2.3.0"
    const val timber = "4.7.1"
    const val firestore = "21.4.3"
    const val firebase_analytics = "17.4.3"
    const val lottie = "3.4.1"
    const val corutines = "1.3.9"
    const val corutines_play_services = "1.3.0"
    const val androidx_fragment = "1.3.0-alpha06"
    const val espressoCore = "3.2.0"
    const val junit = "4.13"
    const val junitAndroid = "1.1.1"
    const val compose = "1.0.0-alpha01"

}

object Kotlin {

    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}"
}


object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.corutines}"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.corutines}"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.corutines}"
    const val playServices =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.corutines_play_services}"

}

object Lifecycle {
    const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
}

object Navigation {
    const val fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val uiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
}

object Compose {
    const val core = "androidx.compose.ui:ui:${Versions.compose}"
    const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val materialIconsExtended =
        "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    const val tooling = "androidx.ui:ui-tooling:${Versions.compose}"
    const val test = "androidx.compose.test:test-core:${Versions.compose}"
    const val uiTest = "androidx.ui:ui-test:${Versions.compose}"
}

object Material {
    const val core = "com.google.android.material:material:${Versions.material}"
}

object Debug {
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object Animation {
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
}


object Di {
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltx}"
    const val hiltAndroidX = "androidx.hilt:hilt-compiler:${Versions.hiltx}"
}


object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:1.2.0-rc01"
    const val coreKtx = "androidx.core:core-ktx:1.5.0-alpha01"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.ConstraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
    const val fragment = "androidx.fragment:fragment:${Versions.androidx_fragment}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.androidx_fragment}"
}

object Firebase {
    const val firestoreKtx = "com.google.firebase:firebase-firestore-ktx:${Versions.firestore}"
    const val firebaseAnalytics =
        "com.google.firebase:firebase-analytics:${Versions.firebase_analytics}"
}


object Test {
    const val junit = "androidx.test.ext:junit:${Versions.junit}"
    const val junitX = "androidx.test.ext:junit:${Versions.junitAndroid}"
    const val core = "androidx.test:core:1.0.0"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoCore}"
    const val espressoWeb = "androidx.test.espresso:espresso-web:${Versions.espressoCore}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espressoCore}"
    const val testRunner = "androidx.test:runner:1.1.0"
    const val testRules = "androidx.test:rules:1.1.0"
    const val archCore = "android.arch.core:core-testing:1.0.0"
    const val mockk = "io.mockk:mockk:1.9"
    const val mockkAndroid = "io.mockk:mockk-android:1.9"
}