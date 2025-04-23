# Allow obfuscation of SLF4J and Logback classes fully (no -keep)
-keepclassmembers,allowobfuscation class ch.qos.logback.** { *; }
-keepclassmembers,allowobfuscation class org.slf4j.** { *; }
-keepclassmembers,allowobfuscation class org.slf4j.impl.** { *; }

# Keep native methods, but allow class names to be obfuscated
-keepclasseswithmembers class * {
    native <methods>;
}

# Allow Ktor debug utilities to be fully obfuscated
-keepclassmembers,allowobfuscation class io.ktor.util.debug.** { *; }

-keep class * extends androidx.room.RoomDatabase { <init>(); }

# Suppress warnings (unchanged)
-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean
-dontwarn io.ktor.util.debug.**
-dontwarn androidx.test.platform.app.InstrumentationRegistry
-dontwarn org.slf4j.impl.StaticLoggerBinder
-dontwarn org.slf4j.impl.StaticMDCBinder
-dontwarn org.slf4j.impl.StaticMarkerBinder