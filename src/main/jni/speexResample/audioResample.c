#include "/speex_resampler.h"
#include <stdio.h>
#include <jni.h>
#include <sys/stat.h>
#include <android/log.h>
#define SINGLE_LENGTH 2048



JNIEXPORT jint JNICALL
Java_org_mirai_zhao_dice_audio_AudioUtil_pcmResample(JNIEnv *env, jobject thiz, jstring src,
                                                     jstring dest, jint current_rate,
                                                     jint new_rate) {
    FILE *fin, *fout;
    int err = -1,readlen;
    spx_uint32_t inlen,outlen;
    short in[SINGLE_LENGTH], out[SINGLE_LENGTH];
    const char *src_c = (*env)->GetStringUTFChars(env,src, 0);
    const char *dest_c = (*env)->GetStringUTFChars(env,dest, 0);
    fin = fopen(src_c, "rb");
    fout = fopen(dest_c, "wb");
    SpeexResamplerState* st = speex_resampler_init(1, current_rate, new_rate, 10, &err);
    do {
        readlen = fread(in, sizeof(short), SINGLE_LENGTH, fin);
        if (readlen > 0) {
            inlen = readlen;
            outlen = SINGLE_LENGTH;
            int ret = speex_resampler_process_int(st, 0, in, &inlen, out, &outlen);
            if (ret == RESAMPLER_ERR_SUCCESS) {
                fwrite(out, sizeof(short), outlen, fout);
            }
        }
    } while (readlen == SINGLE_LENGTH);
    speex_resampler_destroy(st);
    return 0;
}