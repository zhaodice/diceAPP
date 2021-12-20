# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keepattributes *Annotation*,Signature

-keepclasseswithmembers class * extends java.lang.Exception { *;}

#kotlin
-keep class kotlin.comparisons.ComparisonsKt { *; }

-keep class kotlin.collections.IndexedValue { *; }
-keep class kotlin.collections.ArraysKt { *; }
-keep class kotlin.collections.MapsKt { *; }
-keep class kotlin.collections.IntIterator { *; }
-keep class kotlin.collections.CollectionsKt { *; }

-keep class kotlin.text.CharsKt{ *; }
-keep class kotlin.text.Charsets{ *; }
-keep class kotlin.text.Regex{ *; }
-keep class kotlin.text.StringsKt{ *; }
-keep class kotlin.text.MatchGroup{ *; }
-keep class kotlin.text.MatchResult{ *; }
-keep class kotlin.text.MatchGroupCollection{ *; }
-keep class kotlin.text.MatchNamedGroupCollection{ *; }
-keep class kotlin.text.RegexOption{ *; }
-keep class kotlin.text.CharsKt__CharJVMKt{ *; }
-keep class kotlin.text.StringsKt__StringsJVMKt{ *; }
-keep class kotlin.text.Typography{ *; }
-keep class kotlin.text.StringsKt__StringNumberConversionsKt{ *; }
-keep class kotlin.text.StringsKt__StringsKt{ *; }
-keep class kotlin.text.StringsKt___StringsKt{ *; }
-keep class kotlin.text.UStringsKt{ *; }
-keep class kotlin.text.StringsKt__IndentKt{ *; }

-keep class kotlin.sequences.* { *; }
-keep class kotlin.jvm.internal.*{ *; }
-keep class kotlin.jvm.functions.Function1{ *; }
-keep class kotlin.jvm.internal.Lambda{ *; }
-keep class kotlin.jvm.functions.Function2{ *; }
-keep class kotlin.jvm.functions.Function3{ *; }
-keep class kotlin.jvm.functions.Function0{ *; }
-keep class kotlin.jvm.JvmClassMappingKt{ *; }
-keep class kotlin.jvm.internal.PropertyReference1Impl{ *; }
-keep class kotlin.jvm.internal.ArrayIteratorKt{ *; }
-keep class kotlin.jvm.internal.Ref{ *; }
-keep class kotlin.jvm.JvmStatic{ *; }

-keep class kotlin.coroutines.Continuation{ *; }
-keep class kotlin.coroutines.intrinsics.IntrinsicsKt{ *; }
-keep class kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt{ *; }
-keep class kotlin.coroutines.CoroutineContext{ *; }
-keep class kotlin.coroutines.jvm.internal.ContinuationImpl{ *; }
-keep class kotlin.coroutines.jvm.internal.DebugMetadata{ *; }
-keep class kotlin.coroutines.jvm.internal.SuspendLambda{ *; }
-keep class kotlin.coroutines.jvm.internal.Boxing{ *; }
-keep class kotlin.coroutines.jvm.internal.RestrictedSuspendLambda{ *; }
-keep class kotlin.coroutines.AbstractCoroutineContextElement{ *; }
-keep class kotlin.coroutines.EmptyCoroutineContext{ *; }
-keep class kotlin.ranges.IntRange{ *; }
-keep class kotlin.ranges.RangesKt{ *; }
-keep class kotlin.ranges.CharRange{ *; }
-keep class kotlin.ranges.ClosedRange{ *; }
-keep class kotlin.io.FilesKt { *; }
-keep class kotlin.reflect.*{ *; }
-keep class kotlin.random.* { *; }
-keep class kotlin.concurrent.ThreadsKt { *; }
-keep class kotlin.properties.ReadWriteProperty { *; }
-keep class kotlin.contracts.InvocationKind { *; }
-keep class kotlin.contracts.ContractBuilder { *; }
-keep class kotlin.contracts.ExperimentalContracts { *; }
-keep class kotlin.annotation.AnnotationTarget.* { *; }
-keep class kotlin.annotation.AnnotationRetention { *; }
-keep class kotlin.experimental.* { *; }
-keep class kotlin.system.TimingKt { *; }
-keep class kotlin.* { *; }
-keep class kotlin.Metadata { *; }

-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}


-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers enum * { *;}

#fightcent permissionrequest
-keep class fightcent.permissionrequest.** { *; }


# jvm平台的一些不存在的类
-dontwarn java.awt.**
-dontwarn javax.swing.**
-dontwarn sun.misc.**
-dontwarn org.jetbrains.kotlin.**

# ktor
-keep class io.ktor.utils.io.pool.DefaultPool { *; }

#netty
-keepattributes Signature,InnerClasses
-keep class io.netty.bootstrap.Bootstrap { *;}
-keep class io.netty.buffer.AbstractByteBufAllocator { *;}
-keep class io.netty.util.ReferenceCountUtil { *; }
-dontwarn io.netty.**
-dontwarn sun.**

# json
-keep class kotlinx.serialization.protobuf.ProtoNumber { *; }
-keep class kotlinx.serialization.json.** {*;}
-keep class kotlinx.serialization.* {*;}

# gson
-keep class com.google.gson.** {*;}

# toml
-keep class com.moandjiezana.toml.** {*;}

# yaml
-keep class org.yaml.snakeyaml.Yaml {*;}
-keep class org.yaml.snakeyaml.util.* {*;}

#bouncycastle
#-keep class org.bouncycastle.jce.provider.**{ *;}

#-keepclasseswithmembernames class org.bouncycastle.jce.provider.**{ *;}
#-keepclasseswithmembers class org.bouncycastle.jcajce.provider.asymmetric.** {*;}
#-keepclasseswithmembers class org.bouncycastle.jcajce.provider.keystore.** {*;}

# Okhttp3的混淆配置
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*
# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

#rhino
-keep class org.mozilla.classfile.** { *; }
-keep class org.mozilla.javascript.* { *; }
-keep class org.mozilla.javascript.annotations.** { *; }
-keep class org.mozilla.javascript.ast.** { *; }
-keep class org.mozilla.javascript.commonjs.module.** { *; }
-keep class org.mozilla.javascript.commonjs.module.provider.** { *; }
-keep class org.mozilla.javascript.debug.** { *; }
-keep class org.mozilla.javascript.jdk13.** { *; }
-keep class org.mozilla.javascript.jdk15.** { *; }
-keep class org.mozilla.javascript.json.** { *; }
-keep class org.mozilla.javascript.optimizer.** { *; }
-keep class org.mozilla.javascript.regexp.** { *; }
-keep class org.mozilla.javascript.serialize.** { *; }
-keep class org.mozilla.javascript.typedarrays.** { *; }
-keep class org.mozilla.javascript.v8dtoa.** { *; }
-keep class org.mozilla.javascript.xmlimpl.** { *; }
-keep class com.sun.script.javascript.* { *; }

# mirai 配置
-keep enum net.mamoe.mirai.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclasseswithmembers class * extends net.mamoe.mirai.BotFactory{ *;}
-keep class net.mamoe.yamlkt.* {*;}
-keep class net.mamoe.mirai.internal.* { *; }
-keep class net.mamoe.mirai.internal.message.* { *; }
-keep class net.mamoe.mirai.event.** { *; }
-keep class net.mamoe.mirai.message.** { *; }

-keep class net.mamoe.mirai.console.* {*;}
-keep class net.mamoe.mirai.console.internal.** {*;}
-keep class net.mamoe.mirai.console.data.** {*;}
-keep class net.mamoe.mirai.console.plugin.** {*;}
-keep class net.mamoe.mirai.console.util.* {*;}
-keep class net.mamoe.mirai.console.permission.* {*;}

-keep class net.mamoe.mirai.contact.* { *; }
-keep class net.mamoe.mirai.network.* { *; }
-keep class net.mamoe.mirai.utils.* { *; }
-keep class net.mamoe.mirai.* { *; }
-keep interface net.mamoe.mirai.* { *; }
# mirai 配置 2
-dontwarn
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-keepclassmembernames class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}
# Fields used by atomicfu
-keepclassmembers class net.mamoe.mirai.** {
    volatile <fields>;
}
-keepclassmembernames class net.mamoe.mirai.** {
    volatile <fields>;
}
-keepclassmembernames class ** extends kotlinx.serialization.internal.GeneratedSerializer
-keep enum net.mamoe.mirai.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class kotlinx.coroutines.CoroutineScopeKt{ *; }
-keep class kotlinx.coroutines.CoroutineScope{ *; }
-keep class kotlinx.coroutines.CoroutineExceptionHandler{ *; }
-keep class kotlinx.coroutines.BuildersKt{ *; }
-keep class kotlinx.coroutines.DelayKt{ *; }
-keep class kotlinx.coroutines.Job{ *; }
-keep class kotlinx.coroutines.BuildersKt__Builders_commonKt{ *; }
-keep class kotlinx.coroutines.CoroutineName{ *; }
-keep class kotlinx.coroutines.BuildersKt__BuildersKt{ *; }
-keep class kotlinx.coroutines.ExceptionsKt{ *; }
-keep class kotlinx.coroutines.sync.Mutex{ *; }
-keep class kotlinx.coroutines.sync.MutexKt{ *; }
-keep class kotlinx.coroutines.CoroutineDispatcher{ *; }
-keep class kotlinx.coroutines.Dispatchers{ *; }
-keep class kotlinx.coroutines.CoroutineStart{ *; }
-keep class kotlinx.coroutines.flow.FlowCollector{ *; }
-keep class kotlinx.coroutines.ChildHandle{ *; }
-keep class kotlinx.coroutines.ChildJob{ *; }
-keep class kotlinx.coroutines.CompletableJob{ *; }
-keep class kotlinx.coroutines.DisposableHandle{ *; }
-keep class kotlinx.coroutines.InternalCoroutinesApi{ *; }
-keep class kotlinx.coroutines.SupervisorKt{ *; }
-keep class kotlinx.coroutines.Deferred{ *; }
-keep class kotlinx.coroutines.InterruptibleKt{ *; }


-keepnames class net.mamoe.mirai.Mirai

#JVM/DALVIK SYSTEM
-keep class java.nio.** {*;}
-keep class java.lang.** {*;}
-keep class java.lang.management.** {*;}
-keep class javax.script.** {*;}
-keep class dalvik.system.** {*;}
-keep class sun.misc.Service {*;}
-keep class sun.misc.ServiceConfigurationError {*;}
-keep class sun.misc.VM {*;}
-keep class sun.reflect.Reflection {*;}
-keep class sun.security.action.GetPropertyAction {*;}
-keep class sun.security.util.SecurityConstants {*;}
-keep interface sun.security.util.PermissionFactory {*;}

#去掉空检查
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkNotNull(...);
    static void checkParameterIsNotNull(...);
    static void checkNotNullExpressionValue(...);
    static void checkNotNullParameter(...);
    static void checkExpressionValueIsNotNull(...);
    static void throwUninitializedPropertyAccessException(...);
}
-assumenosideeffects class java.util.Objects {
    static *** requireNonNull(...);
}
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** e(...);
    public static *** i(...);
    public static *** v(...);
    public static *** println(...);
    public static *** w(...);
    public static *** wtf(...);
}