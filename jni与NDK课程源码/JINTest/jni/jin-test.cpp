#include <jni.h>
#include <stdio.h>
#include<android/log.h>

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "native-activity", __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "native-activity", __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "native-activity", __VA_ARGS__))

//被extern "C"修饰的变量和函数是按照C语言方式编译和连接的
extern "C" {
JNIEXPORT jobject JNICALL Java_com_opensource_jni_MainActivity_createJNIObject(
		JNIEnv *env, jclass clazz);
}

JNIEXPORT jobject JNICALL Java_com_opensource_jni_MainActivity_createJNIObject(
		JNIEnv *env, jclass clazz) {
	jclass targetClass;
	jmethodID mid;
	jobject newObject;

	jstring helloStr;
	jfieldID fid;
	jint staticIntField;
	jint result;

	//获取MainActivity的 saticIntField
	fid = env->GetStaticFieldID(clazz, "saticIntField", "I");
	staticIntField = env->GetStaticIntField(clazz, fid);
	LOGI("[CPP]get MainActivity saticIntField\n");
	LOGI("MainActivity.saticIntField=%d\n", staticIntField);

	//find the class
	targetClass = env->FindClass("com/opensource/jni/JNITest");

	//get the method
	mid = env->GetMethodID(targetClass, "<init>", "(I)V");

	//generate the object
	printf("[CPP]new JNITest\n");
	newObject = env->NewObject(targetClass, mid, 100);

	//call the method
	mid = env->GetMethodID(targetClass, "callByNative", "(I)I");
	result = env->CallIntMethod(newObject, mid, 1000);

	//set the num
	fid = env->GetFieldID(targetClass, "num", "I");
	LOGI("[CPP]set JNITest num 1000\n");
	env->SetIntField(newObject, fid, result);

	//返回对象的引用
	return newObject;

}
