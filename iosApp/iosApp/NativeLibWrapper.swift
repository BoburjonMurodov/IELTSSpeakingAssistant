//
//  NativeLibWrapper.swift
//  iosApp
//
//  Created by Boburjon Murodov on 13/03/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import Foundation

@objcMembers
class NativeLibWrapper: NSObject {
    @objc static func getBaseUrl() -> String {
        if let cString = getDecryptedBaseUrl() {
            return String(cString: cString)
        }
        return ""
    }
}

