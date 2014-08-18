#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<pthread.h>

#include<jni.h>
#include<android/log.h>

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "native-activity", __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "native-activity", __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "native-activity", __VA_ARGS__))

//��extern "C"���εı����ͺ����ǰ���C���Է�ʽ��������ӵ�
extern "C" {
JNIEXPORT void Java_com_opensource_jni_MainActivity_mainThread(JNIEnv *env,
		jobject obj);
JNIEXPORT void Java_com_opensource_jni_MainActivity_setJNIEnv(JNIEnv *env,
		jobject obj);
}
//�߳���
#define NUMTHREADS 5

//ȫ�ֱ���
JavaVM *g_jvm = NULL;
jobject g_obj = NULL;

void *thread_fun(void* arg) {
	JNIEnv *env;
	jclass cls;
	jmethodID mid;

	//Attach���߳�
	if (g_jvm->AttachCurrentThread(&env, NULL) != JNI_OK) {
		LOGE("%s: AttachCurrentThread() failed", __FUNCTION__);
		return NULL;
	}
	//�ҵ���Ӧ����
	cls = env->GetObjectClass(g_obj);
	if (cls == NULL) {
		LOGE("FindClass() Error.....");
		goto error;
	}
	//�ٻ�����еķ���
	mid = env->GetStaticMethodID(cls, "fromJNI", "(I)V");
	if (mid == NULL) {
		LOGE("GetMethodID() Error.....");
		goto error;
	}
	//������java�еľ�̬����
	env->CallStaticVoidMethod(cls, mid, (int) arg);

	error:
	//Detach���߳�
	if (g_jvm->DetachCurrentThread() != JNI_OK) {
		LOGE("%s: DetachCurrentThread() failed", __FUNCTION__);
	}

	//�˳��̣߳������̵߳�������Ϊ
	pthread_exit(0);
}

//��java�����Դ������߳�
JNIEXPORT void Java_com_opensource_jni_MainActivity_mainThread(JNIEnv *env,
		jobject obj) {
	int i;
	pthread_t pt[NUMTHREADS];

	for (i = 0; i < NUMTHREADS; i++)
		//�������߳�
		pthread_create(&pt[i], NULL, &thread_fun, (void *) i);
}

//��java����������JNI����
JNIEXPORT void Java_com_opensource_jni_MainActivity_setJNIEnv(JNIEnv *env,
		jobject obj) {
	//����ȫ��JVM�Ա������߳���ʹ��
	env->GetJavaVM(&g_jvm);
	//����ֱ�Ӹ�ֵ(g_obj = obj)
	g_obj = env->NewGlobalRef(obj);
}

//����̬�ⱻ����ʱ���������ϵͳ����
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	JNIEnv* env = NULL;
	jint result = -1;

	//��ȡJNI�汾
	if (vm->GetEnv( (void**) &env, JNI_VERSION_1_4) != JNI_OK) {
		LOGE("GetEnv failed!");
		return result;
	}

	return JNI_VERSION_1_4;
}
