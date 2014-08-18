#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<pthread.h>

#include<jni.h>
#include<android/log.h>

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "native-activity", __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "native-activity", __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "native-activity", __VA_ARGS__))

//被extern "C"修饰的变量和函数是按照C语言方式编译和连接的
extern "C" {
JNIEXPORT void Java_com_opensource_jni_MainActivity_mainThread(JNIEnv *env,
		jobject obj);
JNIEXPORT void Java_com_opensource_jni_MainActivity_setJNIEnv(JNIEnv *env,
		jobject obj);
}
//线程数
#define NUMTHREADS 5

//全局变量
JavaVM *g_jvm = NULL;
jobject g_obj = NULL;

void *thread_fun(void* arg) {
	JNIEnv *env;
	jclass cls;
	jmethodID mid;

	//Attach主线程
	if (g_jvm->AttachCurrentThread(&env, NULL) != JNI_OK) {
		LOGE("%s: AttachCurrentThread() failed", __FUNCTION__);
		return NULL;
	}
	//找到对应的类
	cls = env->GetObjectClass(g_obj);
	if (cls == NULL) {
		LOGE("FindClass() Error.....");
		goto error;
	}
	//再获得类中的方法
	mid = env->GetStaticMethodID(cls, "fromJNI", "(I)V");
	if (mid == NULL) {
		LOGE("GetMethodID() Error.....");
		goto error;
	}
	//最后调用java中的静态方法
	env->CallStaticVoidMethod(cls, mid, (int) arg);

	error:
	//Detach主线程
	if (g_jvm->DetachCurrentThread() != JNI_OK) {
		LOGE("%s: DetachCurrentThread() failed", __FUNCTION__);
	}

	//退出线程，这是线程的主动行为
	pthread_exit(0);
}

//由java调用以创建子线程
JNIEXPORT void Java_com_opensource_jni_MainActivity_mainThread(JNIEnv *env,
		jobject obj) {
	int i;
	pthread_t pt[NUMTHREADS];

	for (i = 0; i < NUMTHREADS; i++)
		//创建子线程
		pthread_create(&pt[i], NULL, &thread_fun, (void *) i);
}

//由java调用来建立JNI环境
JNIEXPORT void Java_com_opensource_jni_MainActivity_setJNIEnv(JNIEnv *env,
		jobject obj) {
	//保存全局JVM以便在子线程中使用
	env->GetJavaVM(&g_jvm);
	//不能直接赋值(g_obj = obj)
	g_obj = env->NewGlobalRef(obj);
}

//当动态库被加载时这个函数被系统调用
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	JNIEnv* env = NULL;
	jint result = -1;

	//获取JNI版本
	if (vm->GetEnv( (void**) &env, JNI_VERSION_1_4) != JNI_OK) {
		LOGE("GetEnv failed!");
		return result;
	}

	return JNI_VERSION_1_4;
}
