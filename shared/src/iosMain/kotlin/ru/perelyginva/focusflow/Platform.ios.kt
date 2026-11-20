package ru.perelyginva.focusflow

import androidx.compose.runtime.Composable
import platform.UIKit.UIDevice
import platform.UIKit.UITraitCollection
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.currentTraitCollection

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@Composable
actual fun isSystemInDarkTheme(): Boolean {
    return UITraitCollection.currentTraitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
}