LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := jni-thread
LOCAL_SRC_FILES := jni-thread.cpp

LOCAL_LDLIBS    := -llog

include $(BUILD_SHARED_LIBRARY)
