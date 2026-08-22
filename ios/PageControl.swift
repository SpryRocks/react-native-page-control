class HybridPageControl : HybridPageControlSpec {
  var view = UIPageControl()

  var numberOfPages: Double {
    get { Double(view.numberOfPages) }
    set { view.numberOfPages = Int(newValue) }
  }
  
  var currentPage: Double {
    get { Double(view.currentPage) }
    set { view.currentPage = Int(newValue) }
  }
  
  var pageIndicatorTintColor: String? {
    get { view.pageIndicatorTintColor?.hexString }
    set { view.pageIndicatorTintColor = newValue.flatMap { UIColor(hex: $0) }  }
  }
}

extension UIColor {
    // Создание цвета из Hex-строки (#RRGGBB)
    convenience init(hex: String, default defaultColor: UIColor = .gray) {
        let scanner = Scanner(string: hex.replacingOccurrences(of: "#", with: ""))
        var rgb: UInt64 = 0
        if scanner.scanHexInt64(&rgb) {
            let r = CGFloat((rgb & 0xFF0000) >> 16) / 255.0
            let g = CGFloat((rgb & 0x00FF00) >> 8) / 255.0
            let b = CGFloat(rgb & 0x0000FF) / 255.0
            self.init(red: r, green: g, blue: b, alpha: 1.0)
        } else {
            self.init(cgColor: defaultColor.cgColor)
        }
    }

    // Получение Hex-строки из UIColor
    var hexString: String {
        var r: CGFloat = 0, g: CGFloat = 0, b: CGFloat = 0, a: CGFloat = 0
        getRed(&r, green: &g, blue: &b, alpha: &a)
        return String(format: "#%02X%02X%02X", Int(r * 255), Int(g * 255), Int(b * 255))
    }
}
