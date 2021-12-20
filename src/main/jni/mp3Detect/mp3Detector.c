#pragma once

#include <stdio.h>
#include <jni.h>

#ifdef __cplusplus
extern "C"
{
#endif

#define MP3DetectFrameMaxRange 16384
#define MP3DetectFrameOffsetTolerance 6

enum Internal_MP3Detect_MPEGVersion
{
    MPEG25 = 0,
    MPEGReserved,
    MPEG2,
    MPEG1
};

enum Internal_MP3Detect_MPEGLayer
{
    Layer1,
    Layer2,
    Layer3,
    LayerReserved
};

enum Internal_MP3Detect_Emphasis
{
    EmphasisNone = 0,
    Emphasis5015,
    EmphasisReserved,
    EmphasisCCITJ17
};

enum Internal_MP3Detect_ChannelMode
{
    Stereo,
    JoinStereo,
    DualChannel,
    SingleChannel
};

struct Internal_MP3Detect_Header
{
    enum Internal_MP3Detect_ChannelMode channelMode;

    enum Internal_MP3Detect_MPEGVersion version;
    enum Internal_MP3Detect_MPEGLayer layer;
    enum Internal_MP3Detect_Emphasis emphasis;

    char lsf;

    int bitrate;
    int samplesPerSec;
    int samplesPerFrame;
    int paddingSize;
    short allocationTableIndex;

    char copyright, _private, original;
    char crc;
    char modeExt;
    short bound;
};

 size_t internal_mp3detect_calculate_mp3_frame_size(struct Internal_MP3Detect_Header *head)
{
    int coefficients[2][3] = {
            {   // MPEG 1
                    12,     // Layer 1 (multiply by 4 for slot size)
                    144,    // Layer 2
                    144     // Layer 3
            },
            {   // MPEG 2, MPEG 2.5
                    12,     // Layer 1 (multiply by 4 for slot size)
                    144,    // Layer 2
                    72      // Layer 3
            }
    };
    int slotSizes[3] = {
            4,  // Layer 1
            1,  // Layer 2
            1   // Layer 3
    };
    return
            (size_t) (((coefficients[head->lsf][head->layer] * head->bitrate / head->samplesPerSec) + head->paddingSize)) *
            slotSizes[head->layer];
}

char internal_mp3detect_is_mp3_header_mono(struct Internal_MP3Detect_Header *header)
{
    return header->channelMode == SingleChannel;
}

char internal_mp3detect_mp3_header_matches(struct Internal_MP3Detect_Header *header, struct Internal_MP3Detect_Header *other)
{
    if (other->version != header->version)
        return 0;

    if (other->layer != header->layer)
        return 0;

    if (other->samplesPerSec != header->samplesPerSec)
        return 0;

    if (internal_mp3detect_is_mp3_header_mono(other) != internal_mp3detect_is_mp3_header_mono(header))
        return 0;

    if (other->emphasis != header->emphasis)
        return 0;

    return 1;
}

char internal_mp3detect_init_mp3_header(struct Internal_MP3Detect_Header *head, const unsigned char *header)
{
    head->version = (enum Internal_MP3Detect_MPEGVersion) ((header[1] >> 3) & 0x03);
    if (head->version == MPEGReserved)
        return 0;

    if (head->version == MPEG1)
        head->lsf = 0;
    else
        head->lsf = 1;

    head->layer = (enum Internal_MP3Detect_MPEGLayer) (3 - ((header[1] >> 1) & 0x03));
    if (head->layer == LayerReserved)
        return 0;

    head->crc = !((header[1]) & 0x01);

    char bitrateIndex = (unsigned char) ((header[2] >> 4) & 0x0F);
    if (bitrateIndex == 0x0F)
        return 0;

    int bitrates[2][3][15] = {
            {   // MPEG 1
                    {0, 32, 64, 96, 128, 160, 192, 224, 256, 288, 320, 352, 384, 416, 448,},    // Layer 1
                    {0, 32, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 384,},       // Layer 2
                    {0, 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320,}         // Layer 3
            },
            {   // MPEG 2, MPEG 2.5
                    {0, 32, 48, 56, 64,  80,  96,  112, 128, 144, 160, 176, 192, 224, 256,},    // Layer 1
                    {0, 8,  16, 24, 32, 40, 48, 56,  64,  80,  96,  112, 128, 144, 160,},       // Layer 2
                    {0, 8,  16, 24, 32, 40, 48, 56, 64,  80,  96,  112, 128, 144, 160,}         // Layer 3
            }
    };

    head->bitrate = bitrates[head->lsf][head->layer][bitrateIndex] * 1000;

    if (head->bitrate == 0)
        return 0;

    char bIndex = (unsigned char) ((header[2] >> 2) & 0x03);
    if (bIndex == 0x03)
        return 0;

    int samplingRates[4][3] = {
            {11025, 12000, 8000,},  // MPEG 2.5
            {0,     0,     0,},     // Reserved
            {22050, 24000, 16000,}, // MPEG 2
            {44100, 48000, 32000}   // MPEG 1
    };

    head->samplesPerSec = samplingRates[head->version][bIndex];

    head->paddingSize = 1 * ((header[2] >> 1) & 0x01);

    int samplesPerFrames[2][3] = {
            {   // MPEG 1
                    384,    // Layer 1
                    1152,   // Layer 2
                    1152    // Layer 3
            },
            {   // MPEG 2, MPEG 2.5
                    384,    // Layer 1
                    1152,   // Layer 2
                    576     // Layer 3
            }
    };
    head->samplesPerFrame = samplesPerFrames[head->lsf][head->layer];

    head->_private = (header[2]) & 0x01;

    head->channelMode = (enum Internal_MP3Detect_ChannelMode) ((header[3] >> 6) & 0x03);

    head->modeExt = (unsigned char) ((header[3] >> 4) & 0x03);

    if (head->channelMode == JoinStereo)
        head->bound = 4 + head->modeExt * 4;

    head->copyright = (header[3] >> 3) & 0x01;

    head->original = (header[3] >> 2) & 0x01;

    head->emphasis = (enum Internal_MP3Detect_Emphasis) ((header[3]) & 0x03);
    if (head->emphasis == EmphasisReserved)
        return 0;

    if (head->layer == Layer2)
    {
        if (head->version == MPEG1)
        {

            char allowedModes[15][2] = {
                    {1, 1},  // free mode
                    {0, 1},  // 32
                    {0, 1},  // 48
                    {0, 1},  // 56
                    {1, 1},  // 64
                    {0, 1},  // 80
                    {1, 1},  // 96
                    {1, 1},  // 112
                    {1, 1},  // 128
                    {1, 1},  // 160
                    {1, 1},  // 192
                    {1, 0}, // 224
                    {1, 0}, // 256
                    {1, 0}, // 320
                    {1, 0}  // 384
            };

            if (!allowedModes[bitrateIndex][internal_mp3detect_is_mp3_header_mono(head)])
                return 0;

            switch (head->bitrate / 1000 / (internal_mp3detect_is_mp3_header_mono(head) ? 1 : 2))
            {
                case 32:
                case 48:
                    if (head->samplesPerSec == 32000)
                        head->allocationTableIndex = 3;
                    else
                        head->allocationTableIndex = 2;
                    break;
                case 56:
                case 64:
                case 80:
                    if (head->samplesPerSec != 48000)
                    {
                        head->allocationTableIndex = 0;
                        break;
                    }
                case 96:
                case 112:
                case 128:
                case 160:
                case 192:
                    if (head->samplesPerSec != 48000)
                    {
                        head->allocationTableIndex = 1;
                        break;
                    } else
                    {
                        head->allocationTableIndex = 0;
                    }
                    break;
            }
        } else
            head->allocationTableIndex = 4;
    }

    return 1;
}

char internal_mp3detect_read_mp3_header(struct Internal_MP3Detect_Header *head, FILE *stream, size_t *offset, char exactOffset,
                                               struct Internal_MP3Detect_Header *lastHeader)
{
    head->allocationTableIndex = 0;
    head->bound = 32;

    int step = 1;

    while (!feof(stream))
    {
        unsigned char data[4];
        fseek(stream, *offset, SEEK_SET);
        fread(data, 1, 4, stream);

        if (data[0] == 0xFF && ((data[1] & 0xE0) == 0xE0) && ((data[2] & 0xF0) != 0xF0))
        {
            if (!internal_mp3detect_init_mp3_header(head, data))
                return 0;

            if (lastHeader && !internal_mp3detect_mp3_header_matches(head, lastHeader))
                return 0;

            return 1;
        }

        if (!exactOffset)
        {
            *offset++;

            if (step > MP3DetectFrameMaxRange)
                return 0;

            step++;
        } else
        {
            if (step > MP3DetectFrameOffsetTolerance)
                return 0;

            if (step % 2 == 1)
                *offset += step;
            else
                *offset -= step;

            step++;
        }
    }

    return 0;
}

void internal_mp3detect_skip_id3_tags(FILE *stream, size_t *offset)
{
    unsigned char data[10];
    fseek(stream, *offset, SEEK_SET);
    fread(data, 1, 10, stream);

    if (data[0] != 'I' || data[1] != 'D' || data[2] != '3')
        return;

    *offset += 10;

    // syncsafe int
    *offset += data[6] << 21 | data[7] << 14 | data[8] << 7 | data[9];

    // is footer bit set?
    if ((data[5] & 0x10) != 0)
        *offset += 10; // skip footer
}

char internal_mp3detect_read_mp3_frame(FILE *stream, size_t *offset,
                                              char findSubFrames,
                                              char exactOffset,
                                              struct Internal_MP3Detect_Header *compareHeader)
{
    struct Internal_MP3Detect_Header header;
    if (!internal_mp3detect_read_mp3_header(&header, stream, offset, exactOffset, compareHeader))
        return 0;
    size_t frameSize = internal_mp3detect_calculate_mp3_frame_size(&header);

    if (findSubFrames)
    {
        size_t newOffset = *offset + frameSize;
        return internal_mp3detect_read_mp3_frame(stream, &newOffset, 0, 1, &header);
    }

    return 1;
}

char mp3detect_is_mp3_file(const char *path)
{
    size_t offset = 0;
    FILE *stream = fopen(path, "rb");
    if (stream == 0){
        return 0;
    }
    internal_mp3detect_skip_id3_tags(stream, &offset);
    return internal_mp3detect_read_mp3_frame(stream, &offset, 1, 0, NULL);
}

JNIEXPORT jboolean JNICALL
Java_org_mirai_zhao_dice_audio_AudioUtil_mp3Detect(JNIEnv *env, jobject thiz, jstring src) {
    const char *src_c = (*env)->GetStringUTFChars(env,src, 0);
    return mp3detect_is_mp3_file(src_c);
}

#ifdef __cplusplus
}
#endif


