// The authors disclaim copyright to this source code

#include <jni.h>
#include <string.h>

#ifdef __cplusplus
extern "C" {
#endif
extern char *cb(const char *id, const char *event, const char *err, const char *data);

        JNIEXPORT jstring JNICALL
Java_com_cod5_deafblind_MainActivity_cbToJNI(
        JNIEnv* env,
jobject self, jstring id, jstring event, jstring err, jstring data) {
        const char *a, *b, *c, *d;
        a = env->GetStringUTFChars(id, 0);
        b = env->GetStringUTFChars(event, 0);
        c = env->GetStringUTFChars(err, 0);
        d = env->GetStringUTFChars(data, 0);
        return env->NewStringUTF(cb(a, b, c, d));
}

#ifdef __cplusplus
}
#endif