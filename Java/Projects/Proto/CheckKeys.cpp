// CheckKeys.cpp : Определяет экспортированные функции для приложения DLL.
//

#include "stdafx.h"
#include <jni.h>        // JNI header provided by JDK
#include <stdio.h>      // C Standard IO Header
#include <time.h>
#include "Proto.h"

#include <cstdlib>
#define SKP_SOFT_PROTECTED
#include "SKPSoftCall.h"
#include "DeviceCode.h"

//#KPO11
using namespace std;
typedef struct param1_s {
	unsigned char keys1[3];
	unsigned short args1[500];
	long length1;// передаваемые переменные
} param1_t;
SKPSOFTRETURNCODE func1(param1_t* func_params) { // защищаемая функция	
	for (int i = 0; i < func_params->length1-4; i++) {
		if (func_params->args1[i] == ' ') {
			if (func_params->args1[i + 1] == '-' && func_params->args1[i + 2] == 'g' && func_params->args1[i + 3] == 'e'
				&& func_params->args1[i + 4] == 'n' && func_params->args1[i + 5] == ' ') {
				func_params->keys1[0] = true;
				break;
			}
		}
	}

	for (int i = 0; i < func_params->length1 - 6; i++) {
		if (func_params->args1[i] == ' ') {
			if (func_params->args1[i + 1] == '-' && func_params->args1[i + 2] == 'c' && func_params->args1[i + 3] == 'o'
				&& func_params->args1[i + 4] == 'o' && func_params->args1[i + 5] == 'r' && func_params->args1[i + 6] == 'd'
				&& func_params->args1[i + 7] == ' ')
				func_params->keys1[1] = true;
			if (func_params->args1[i + 1] == '-' && func_params->args1[i + 2] == 'g' && func_params->args1[i + 3] == 'r'
				&& func_params->args1[i + 4] == 'o' && func_params->args1[i + 5] == 'u' && func_params->args1[i + 6] == 'p'
				&& func_params->args1[i + 7] == ' ')
				func_params->keys1[2] = true;
		}
	}
	return SKRC_SECCESS;
}
//#KPC11


JNIEXPORT jbooleanArray JNICALL Java_Proto_checkKeys
(JNIEnv *env, jobject, jbooleanArray keys, jcharArray args, jint length) {
	param1_t params;
	jboolean *keysArr = env->GetBooleanArrayElements(keys,NULL);
	for (int i = 0; i < 3; i++)
		params.keys1[i] = keysArr[i];
	jchar *argsArr = env->GetCharArrayElements(args,NULL);
	for (int i = 0; i < length; i++)
		params.args1[i] = argsArr[i];
	params.length1 = length;
	SKPSoftSetProgramName(_T("Преобразователь"));
	SKPSoftSetLicenseLength(_T("6 месяцев"));
	int err = SKP_SOFT_CALL(func1, 41, 0x11, params, param1_t);
	if (err != 0) {
		printf("Defend error\n");
		exit(1);
	}
	SKPSOFTRETURNCODE err1 = (SKPSOFTRETURNCODE)err;
	SKPSoftGetStartLicenseDate(41,&err1);
	SKPSoftGetEndLicenseDate(41, &err1);
	env->SetBooleanArrayRegion(keys, 0, 3, params.keys1);
	return keys;
}

