package org.mirai.zhao.dice.audio

object AudioUtil {
    init{
        System.loadLibrary("silkV3Encoder")
        System.loadLibrary("speexResample")
        System.loadLibrary("mp3Detector")
    }
    external fun mp3ToPCM(src: String?, dest: String?): Int
    external fun pcmToSilk(src: String?, dest: String?, rate: Int): Int
    external fun pcmResample(src:String, dest:String, current_rate:Int, new_rate:Int):Int
    external fun mp3Detect(src:String):Boolean
}