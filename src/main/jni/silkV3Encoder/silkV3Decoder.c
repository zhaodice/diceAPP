//
// Created by ketian on 16-9-23.
//

#include <jni.h>

#ifdef __cplusplus
extern "C"
{
#endif

#include "silk.h"
#include "easy_mad.h"




JNIEXPORT jint JNICALL
Java_org_mirai_zhao_dice_audio_AudioUtil_mp3ToPCM(JNIEnv *env, jobject thiz, jstring src,
                                                  jstring dest) {
    const char *src_c = (*env)->GetStringUTFChars(env,src, 0);
    const char *dest_c = (*env)->GetStringUTFChars(env,dest, 0);
    LOGD("mp3->pcm convert %s to %s", src_c, dest_c);
    if (convertMP32PCM(src_c, dest_c) == -1) {
        LOGD("mp3_decode failed %s", dest_c);
        return -1;
    }
    LOGD("mp3->pcm converted");
    return 0;
}

JNIEXPORT jint JNICALL
Java_org_mirai_zhao_dice_audio_AudioUtil_pcmToSilk(JNIEnv *env, jobject thiz, jstring src,
                                                   jstring dest, jint rate) {
    const char *src_c = (*env)->GetStringUTFChars(env,src, 0);
    const char *dest_c = (*env)->GetStringUTFChars(env,dest, 0);
    FILE *silk = fopen(dest_c, "wb+");
    LOGD("pcm->silk convert %s to %s", src_c, dest_c);
    if (convertPCM2Silk(src_c, silk, rate) != 0) {
        LOGD("convert pcm to silk failed");
        return -1;
    }
    LOGD("pcm->silk converted");
    fclose(silk);
    return 0;
}

#ifdef __cplusplus
}
#endif





