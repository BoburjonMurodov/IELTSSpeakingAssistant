#include <jni.h>
#include <string>

// XOR Decryption Function
std::string xorDecrypt(const std::string &input, char key) {
    std::string output = input;
    for (size_t i = 0; i < input.size(); i++) {
        output[i] = input[i] ^ key;  // XOR decryption
    }
    return output;
}

// Store the Correctly Encrypted URL
std::string encryptedString = R"(2..*)`uu>8,3(./;6?>/9;.354t957u3?6.)u;*3u)";

extern "C"
JNIEXPORT jstring JNICALL
Java_com_boboor_speaking_utils_NativeLib_getBaseUrl(JNIEnv *env, jobject thiz) {
    char key = 0x5A; // Same key as encryption
    std::string decrypted = xorDecrypt(encryptedString, key);
    return env->NewStringUTF(decrypted.c_str());
}