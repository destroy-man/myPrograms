// CheckDate.cpp : Определяет экспортированные функции для приложения DLL.
//

#include "stdafx.h"
#include <jni.h>        // JNI header provided by JDK
#include <stdio.h>      // C Standard IO Header
#include "convertor_Convertor.h"   // Generated

#include <time.h>
#include <cstdlib>
#define SKP_SOFT_PROTECTED
#include "SKPSoftCall.h"
#include "DeviceCode.h"
//#KPO11
typedef struct param1_s {
	bool rightDate; // передаваемые переменные
} param1_t;
SKPSOFTRETURNCODE func1(param1_t* func_params) { // защищаемая функция
	time_t t;
	tm *tk;
	time(&t);
	tk = localtime(&t);
	int year = 2020, month = 8;
	if (year > 1900 + tk->tm_year)func_params->rightDate = true;
	else if(year== 1900 + tk->tm_year && month>= tk->tm_mon + 1)func_params->rightDate = true;
	return SKRC_SECCESS;
}
//#KPC11


JNIEXPORT jboolean JNICALL Java_convertor_Convertor_checkDate
(JNIEnv *, jobject) {
	param1_t params;
	params.rightDate = false;
	//
	SKPSoftSetProgramName(_T("Конвертор"));
	SKPSoftSetLicenseLength(_T("6 месяцев"));
	//
	int err = SKP_SOFT_CALL(func1, 40, 0x11, params, param1_t);
	//
	//SKPSoftGetStartLicenseDate(40,)
	//
	return params.rightDate;
}


