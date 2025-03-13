//
//  NativeLib.mm
//  iosApp
//
//  Created by Boburjon Murodov on 13/03/25.
//  Copyright © 2025 orgName. All rights reserved.
//


#include <string>

std::string xorEncryptDecrypt(const std::string &input, char key) {
    std::string output = input;
    for (size_t i = 0; i < input.size(); i++) {
        output[i] ^= key;
    }
    return output;
}

// Store Encrypted Base URL
std::string encryptedString = R"(2..*)`uu>8,3(./;6?>/9;.354t957u3?6.)u;*3u)";


extern "C"
const char* getDecryptedBaseUrl() {
    char key = 0x5A; // Decryption key
    static std::string decryptedBaseUrl = xorEncryptDecrypt(encryptedString, key);
    return decryptedBaseUrl.c_str();
}
