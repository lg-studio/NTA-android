LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := vorbis-stream
LOCAL_CFLAGS += -I$(LOCAL_PATH)/../include -fsigned-char



LOCAL_SHARED_LIBRARIES := libogg libvorbis

LOCAL_SRC_FILES := \
	vorbis-fileoutputstream.c \
	vorbis-fileinputstream.c \
	jni-util.c

include $(BUILD_SHARED_LIBRARY)
