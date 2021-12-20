package org.mirai.zhao.dice

import kotlinx.coroutines.CompletableDeferred

object ApplicationLoginSolver {
    private lateinit var captchaData: ByteArray
    private lateinit var verificationResult: CompletableDeferred<String>
    suspend fun awaitResult():String{
        return if(this::verificationResult.isInitialized) {
            verificationResult.await()
        }else{
            ""
        }
    }
    fun setResult(str:String):Boolean{
        return if(this::verificationResult.isInitialized) {
            verificationResult.complete(str)
            true
        }else{
            false
        }
    }
    fun resetResult(){
        verificationResult = CompletableDeferred()
    }
    fun setCaptchaImage(data:ByteArray){
        captchaData = data
    }
    fun getCaptchaImage():ByteArray{
        return captchaData
    }
}