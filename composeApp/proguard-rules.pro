# Keep all SLF4J classes
-keep class org.slf4j.** { *; }

# Keep Logback classes (if using Logback)
-keep class ch.qos.logback.** { *; }
# Keep AndroidX Test classes (only if you're using instrumented tests)
#-keep class androidx.test.platform.app.InstrumentationRegistry { *; }

# Keep all SLF4J classes
-keep class org.slf4j.** { *; }
-keep class org.slf4j.impl.** { *; }
-keep class ch.qos.logback.** { *; }

-dontwarn androidx.test.platform.app.InstrumentationRegistry
-dontwarn org.slf4j.impl.StaticLoggerBinder
-dontwarn org.slf4j.impl.StaticMDCBinder
-dontwarn org.slf4j.impl.StaticMarkerBinder