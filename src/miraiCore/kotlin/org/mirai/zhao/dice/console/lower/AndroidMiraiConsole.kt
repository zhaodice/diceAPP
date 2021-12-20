@file:Suppress(
        "EXPERIMENTAL_API_USAGE",
        "DEPRECATION_ERROR",
        "OverridingDeprecatedMember",
        "INVISIBLE_REFERENCE",
        "INVISIBLE_MEMBER"
)

package org.mirai.zhao.dice.console.lower
import android.os.Build
import androidx.annotation.RequiresApi
import org.mirai.zhao.dice.AppContext
import org.mirai.zhao.dice.console.util.AndroidLoginSolver
import org.mirai.zhao.dice.console.util.AndroidMiraiLogger
import android.content.Context
import kotlinx.coroutines.*
import net.mamoe.mirai.console.ConsoleFrontEndImplementation
import net.mamoe.mirai.console.MiraiConsoleFrontEndDescription
import net.mamoe.mirai.console.MiraiConsoleImplementation
import net.mamoe.mirai.console.data.MultiFilePluginDataStorage
import net.mamoe.mirai.console.data.PluginDataStorage
import net.mamoe.mirai.console.plugin.loader.PluginLoader
import net.mamoe.mirai.console.util.ConsoleInput
import net.mamoe.mirai.console.util.NamedSupervisorJob
import net.mamoe.mirai.console.util.SemVersion
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.LoginSolver
import net.mamoe.mirai.utils.MiraiLogger
import org.mirai.zhao.dice.BuildConfig
import java.nio.file.Path
import java.nio.file.Paths

class AndroidMiraiConsole(val context: Context) : MiraiConsoleImplementation,
        CoroutineScope by CoroutineScope(NamedSupervisorJob("MiraiAndroid") + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            AndroidMiraiLogger.error(throwable)
        }
        ) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val workingPath: Path = Paths.get(context.cacheDir.resolve("MiraiWorkspace").apply { mkdirs() }.absolutePath)
    @RequiresApi(Build.VERSION_CODES.O)
    override val rootPath: Path = Paths.get(AppContext.miraiDir).toAbsolutePath()

    override fun createLogger(identity: String?): MiraiLogger = AndroidMiraiLogger

    override fun createLoginSolver(requesterBot: Long, configuration: BotConfiguration): LoginSolver {
        return AndroidLoginSolver
    }

    override val builtInPluginLoaders: List<Lazy<PluginLoader<*, *>>> = listOf(lazy {
        DexPluginLoader(AppContext.pluginsDir)
    })
    override val frontEndDescription: MiraiConsoleFrontEndDescription = AndroidConsoleFrontEndDescImpl
    override val consoleCommandSender: MiraiConsoleImplementation.ConsoleCommandSenderImpl = AndroidConsoleCommandSenderImpl
    override val consoleInput: ConsoleInput
        get() = object : ConsoleInput {
            override suspend fun requestInput(hint: String): String {
                return ""
            }
        }
    @RequiresApi(Build.VERSION_CODES.O)
    override val dataStorageForJvmPluginLoader: PluginDataStorage = MultiFilePluginDataStorage(workingPath.resolve("data"))
    @RequiresApi(Build.VERSION_CODES.O)
    override val dataStorageForBuiltIns: PluginDataStorage = MultiFilePluginDataStorage(workingPath.resolve("data"))
    @RequiresApi(Build.VERSION_CODES.O)
    override val configStorageForJvmPluginLoader: PluginDataStorage = MultiFilePluginDataStorage(workingPath.resolve("config"))
    @RequiresApi(Build.VERSION_CODES.O)
    override val configStorageForBuiltIns: PluginDataStorage = MultiFilePluginDataStorage(workingPath.resolve("config"))
    override val isAnsiSupported: Boolean = false
}


object AndroidConsoleFrontEndDescImpl : MiraiConsoleFrontEndDescription {
    override val name: String get() = "Android"
    override val vendor: String get() = "赵怡然 & Mamoe Technologies"
    override val version: SemVersion = SemVersion(BuildConfig.VERSION_NAME)
}

@ConsoleFrontEndImplementation
object AndroidConsoleCommandSenderImpl : MiraiConsoleImplementation.ConsoleCommandSenderImpl {
    @JvmSynthetic
    override suspend fun sendMessage(message: String) {
        AndroidMiraiLogger.info(message)
    }
    @JvmSynthetic
    override suspend fun sendMessage(message: Message) {
        AndroidMiraiLogger.info(message.contentToString())
    }
}