cmake_minimum_required(VERSION 3.0.0)

SET(SORCES_ROOT src/main/jni)
include_directories(${SORCES_ROOT}/)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -ffunction-sections -fdata-sections -fPIC -O3 -fvisibility=hidden -w ")
set(CMAKE_SHARED_LINKER_FLAGS "-Wl,--gc-sections")

aux_source_directory(${SORCES_ROOT}/mp3Detect JNI_SRC_MP3_DETECTOR)
add_library(mp3Detector SHARED ${JNI_SRC_MP3_DETECTOR})

ADD_DEFINITIONS(-DFIXED_POINT)
ADD_DEFINITIONS(-DUSE_SMALLFT)
aux_source_directory(${SORCES_ROOT}/libspeex LIB_SPEEX)
aux_source_directory(${SORCES_ROOT}/speexResample JNI_SRC_SPEEX_RESAMPLE)
add_library(speexResample SHARED ${LIB_SPEEX} ${JNI_SRC_SPEEX_RESAMPLE})
target_include_directories(speexResample PRIVATE ${SORCES_ROOT}/include/speex)

aux_source_directory(${SORCES_ROOT}/libsilk LIB_SILK)
aux_source_directory(${SORCES_ROOT}/libmad LIB_MAD)
aux_source_directory(${SORCES_ROOT}/silkV3Encoder JNI_SRC_SILK)
add_library(silkV3Encoder SHARED ${LIB_SILK} ${LIB_MAD} ${JNI_SRC_SILK})
target_include_directories(silkV3Encoder PRIVATE ${SORCES_ROOT}/include/silk)

find_library(android-log log)

target_link_libraries(silkV3Encoder ${android-log})