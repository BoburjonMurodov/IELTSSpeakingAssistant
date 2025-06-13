import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    init() {
        let baseUrl = NativeLibWrapper.getBaseUrl()
        IOSUtilsKt.BASE_URl = baseUrl
    }
    
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.container)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}





