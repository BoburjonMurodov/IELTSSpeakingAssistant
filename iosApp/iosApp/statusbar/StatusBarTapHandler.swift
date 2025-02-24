import UIKit
import Foundation

@objc public class StatusBarTapHandler: NSObject {
    private var callback: (() -> Void)?

    @objc public func setupStatusBarTap(callback: @escaping () -> Void) {
        self.callback = callback
        // Add a tap gesture recognizer to the status bar
        if let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
           let statusBarManager = windowScene.statusBarManager {
            let statusBarFrame = statusBarManager.statusBarFrame
            let statusBarView = UIView(frame: statusBarFrame)
            windowScene.windows.first?.addSubview(statusBarView)

            let tapGesture = UITapGestureRecognizer(target: self, action: #selector(statusBarTapped))
            statusBarView.addGestureRecognizer(tapGesture)
        }
    }

    @objc private func statusBarTapped() {
        callback?()
    }
}



@objc public class StatusBarBridge: NSObject {

    private let handler = StatusBarTapHandler()

    @objc public func listenForStatusBarTaps(callback: @escaping () -> Void) {
        handler.setupStatusBarTap(callback: callback)
    }
}
