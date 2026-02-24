# Google Play Store’a Yükleme Rehberi – Sssnap

Bu rehber, Sssnap uygulamasını Google Play Store’a adım adım yüklemeniz için hazırlanmıştır.

---

## Ön koşullar

- **Google Play Console hesabı:** [play.google.com/console](https://play.google.com/console) – **Bir kerelik 25 USD** kayıt ücreti gerekir.
- **Android Studio** ve bu proje bilgisayarınızda açılabiliyor olmalı.
- **En az 1 kez** release için imzalı AAB (Android App Bundle) üretmiş olmalısınız.

---

## Adım 1: Release keystore oluşturma

Uygulamayı Play Store’da güncellerken aynı keystore gerekir. **Bu dosyayı ve şifreleri güvenli yedekleyin; kaybederseniz uygulamayı güncelleyemezsiniz.**

### 1.1 Keystore dosyası oluşturma

PowerShell veya CMD’de proje klasöründe çalıştırın:

```powershell
cd c:\Users\nazli\AndroidStudioProjects\SnakeGame
keytool -genkey -v -keystore release.keystore -alias sssnap -keyalg RSA -keysize 2048 -validity 10000
```

- **Şifre:** Hem keystore hem key için güçlü bir şifre belirleyin (en az 6 karakter).
- **Ad, birim, kurum, şehir, ülke:** Sorulan bilgileri doldurun (gerçek veya uygulama adı yeterli).

Oluşan `release.keystore` dosyasını **güvenli bir yere** kopyalayıp yedekleyin (örn. bulut + USB). Projede kullanacaksanız `composeApp/` altına koyabilirsiniz veya ev dizininde tutabilirsiniz.

### 1.2 Keystore bilgilerini projeye verme

Proje kökünde (SnakeGame klasöründe) `keystore.properties` dosyası oluşturun. Bu dosya **git’e eklenmemeli** (zaten .gitignore’da).

**`keystore.properties`** (yol ve şifreleri kendi değerlerinizle değiştirin):

```properties
storeFile=release.keystore
storePassword=KEISTORE_SIFRENIZ
keyAlias=sssnap
keyPassword=KEY_SIFRENIZ
```

- Keystore’u proje dışında tutuyorsanız tam yolu verin, örn:  
  `storeFile=C:\\Users\\nazli\\keystores\\release.keystore`

Bu adımdan sonra proje release build alırken bu keystore ile imzalanacaktır.

---

## Adım 2: Release AAB üretme

Play Store **AAB (Android App Bundle)** formatını tercih eder; APK de yüklenebilir ama AAB önerilir.

### 2.1 Android Studio’dan

1. **Build → Generate Signed Bundle / APK**
2. **Android App Bundle** seçin → Next
3. **Create new** veya **Choose existing** ile keystore’u seçin (yukarıda oluşturduğunuz).
4. Key alias: `sssnap`, şifreleri girin → Next
5. **release** build type → **Create**

Üretilen dosya:  
`composeApp/release/app-release.aab`

### 2.2 Komut satırından (keystore.properties kullanıyorsanız)

Proje kökünde:

```powershell
cd c:\Users\nazli\AndroidStudioProjects\SnakeGame
.\gradlew :composeApp:bundleRelease
```

AAB dosyası:  
`composeApp\build\outputs\bundle\release\composeApp-release.aab`

Bu AAB’yi Play Console’a yükleyeceksiniz.

---

## Adım 3: Google Play Console’da uygulama oluşturma

1. [Google Play Console](https://play.google.com/console) → Giriş yapın.
2. **“Uygulama oluştur”** (Create app).
3. Uygulama adı: **Sssnap**, varsa varsayılan dil, tür (örn. Oyun), ücretsiz/ücretli seçin.
4. Gerekli bildirimleri (ör. geliştirici program politikaları) kabul edin.

---

## Adım 4: Mağaza sayfası (Store listing)

**Play Console → Uygulamanız → Büyüme → Mağaza ayarları → Ana mağaza sayfası.**

Doldurulacaklar:

| Alan | Açıklama |
|------|----------|
| **Uygulama adı** | Sssnap (max 30 karakter) |
| **Kısa açıklama** | Mağazada listelemede görünen kısa metin (max 80 karakter) |
| **Tam açıklama** | Detaylı açıklama (max 4000 karakter); KVKK/gizlilik notu ekleyebilirsiniz |
| **Uygulama simgesi** | 512x512 px PNG (projedeki snakelogo’dan üretebilirsiniz) |
| **Öne çıkan grafik** | 1024x500 px (isteğe bağlı ama önerilir) |
| **Ekran görüntüleri** | En az 2 adet; telefon için 16:9 veya 9:16, min 320px kısa kenar |

Projede zaten `GIZLILIK_POLITIKASI.md` ve KVKK metni var; açıklamada “Gizlilik politikası uygulama içinde ve mağaza sayfasında yer almaktadır” gibi bir cümle ekleyebilirsiniz.

---

## Adım 5: İçerik derecelendirmesi

**Play Console → Politika ve uygulamalar → Uygulama içeriği → İçerik derecelendirmesi.**

1. Anketi başlatın (Türkiye için yaş grubu / IARC benzeri anket).
2. Soruları uygulamanıza göre cevaplayın (yılan oyunu, reklam yok, vb.).
3. Anketi bitirip derecelendirme sertifikasını alın.

---

## Adım 6: Gizlilik ve güvenlik

- **Gizlilik politikası URL’si:**  
  Eğer politikayı bir web sayfasında yayınlıyorsanız URL’i girin.  
  Yayınlamıyorsanız “Gizlilik politikası uygulama içinde sunulmaktadır” gibi açıklama yapıp gerekli alanı doldurmanız yeterli olabilir (Console’daki talimatlara göre ilerleyin).
- **Veri güvenliği formu:**  
  Uygulama veri toplamıyorsa (KVKK metninizde belirttiğiniz gibi) buna göre “Veri toplanmıyor” vb. seçenekleri işaretleyin.

---

## Adım 7: Sürümü yükleme (Production veya Test)

1. **Play Console → Sürümler → Üretim** (veya **Kapalı test / Açık test**).
2. **Yeni sürüm oluştur** → **Uygulama paketlerini yükle**.
3. `composeApp-release.aab` dosyasını sürükleyip bırakın veya seçin.
4. **Sürüm notları** ekleyin (örn. “İlk sürüm” veya “1.0 – İlk yayın”).
5. **İncelemeye gönder** / **Kaydet**.

---

## Adım 8: İnceleme ve yayın

- Tüm zorunlu bölümler (mağaza sayfası, derecelendirme, gizlilik, vb.) tamamlandıysa **“Gönder”** / **“İncelemeye gönder”** seçeneği aktif olur.
- Gönderdikten sonra inceleme birkaç saat ile birkaç gün sürebilir.
- Onay sonrası uygulama seçtiğiniz ülkelerde (ör. Türkiye) yayına alınır.

---

## Özet kontrol listesi

- [ ] Play Console hesabı açıldı (25 USD ödendi)
- [ ] `release.keystore` oluşturuldu ve güvenli yedeklendi
- [ ] `keystore.properties` oluşturuldu (git’e eklenmedi)
- [ ] Release AAB üretildi (`composeApp-release.aab`)
- [ ] Mağaza sayfası: ad, kısa/tam açıklama, 512x512 ikon, ekran görüntüleri
- [ ] İçerik derecelendirmesi tamamlandı
- [ ] Gizlilik / veri güvenliği bölümleri dolduruldu
- [ ] AAB yüklendi, sürüm notu yazıldı, incelemeye gönderildi

---

## Sık karşılaşılan sorunlar

- **“Upload key’e geçiş”:** İlk yüklemede Play App Signing kullanırsanız, Play Console yükleme anahtarını (upload key) kaydeder. Keystore’unuzu kaybetmemek yine önemli; Play App Signing’i açık bırakmanız önerilir.
- **Version code:** Her yeni yüklemede `versionCode` artmalı. Projede şu an `versionCode = 1`, `versionName = "1.0"`. Sonraki güncellemelerde `build.gradle.kts` içinde bunları artırın (örn. 2 ve "1.1").
- **İmza hatası:** `keystore.properties` yolu ve şifrelerin doğru olduğundan emin olun; `storeFile` tam yol ise Windows’ta `\\` kullanın.

Bu rehberi tamamladığınızda uygulama Play Store’da yayında olacaktır. Belirli bir adımda takılırsanız o adımın numarası ve gördüğünüz hata mesajıyla sorabilirsiniz.
