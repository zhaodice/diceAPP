package org.mirai.zhao.dice.file

import java.io.File

class TempFile(file:File): File(file.absolutePath) {
    var noAutoDelete = false
    override fun renameTo(dest: File): Boolean {
        val result = super.renameTo(dest)
        if(result){
            noAutoDelete = true
        }
        return result
    }
    protected fun finalize() {
        if(noAutoDelete){
            return
        }
        if(exists()) {
            if (isDirectory) {
                deleteRecursively()
            } else if (isFile) {
                delete()
            }
        }
    }
}