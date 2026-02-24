package com.ntapps.sssnap.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ntapps.sssnap.theme.AppColors

private val privacyText = """
KVKK AYDINLATMA METNİ

6698 sayılı Kişisel Verilerin Korunması Kanunu ("KVKK") uyarınca, 
"Sssnap" uygulaması kapsamında kişisel verilerinizin işlenmesine 
ilişkin aşağıdaki açıklamalar yapılmaktadır.

VERİ SORUMLUSU
Uygulama geliştiricisi
İletişim: ntapps.supp@gmail.com

İŞLENEN VERİLER, AMAÇ VE HUKUKİ DAYANAK
1. Galeri Erişimi (Görsel Özelleştirme)
   • İşlenen veri: Cihazınızın galerisinden seçtiğiniz görsel
   • İşleme amacı: Yılan karakterinin görünümünü kişiselleştirmek
   • Hukuki dayanak: KVKK m.5/1-a (Açık rızanız)
   • Saklama süresi: Uygulama çalıştığı sürece (geçici işleme)

2. Diğer Veriler
Uygulama, kullanıcı kimliği, iletişim bilgisi, konum verisi veya davranış analitiği gibi kişisel verileri toplamamakta, kaydetmemekte veya işlememektedir. Uygulama internete bağlanmamakta ve herhangi bir veri aktarımı gerçekleştirmemektedir.

VERİ İŞLEME YÖNTEMİ VE GÜVENLİĞİ
* Seçtiğiniz görsel yalnızca cihazınızda yerel olarak işlenir ve uygulama dışında herhangi bir yere aktarılmaz.
* Görsel hiçbir sunucuya gönderilmez, internet üzerinde aktarılmaz 
  veya bulut hizmetlerinde saklanmaz.
* Uygulama internet bağlantısı kullanmaz, çevrimdışı çalışır.
* Uygulama kapatıldığında veya görsel değiştirildiğinde, önceki 
  görsel otomatik olarak silinir ve geri getirilemez.
* Veri güvenliği, cihazınızın kendi güvenlik ayarlarına bağlıdır.

VERİLERİN AKTARILMASI
Yukarıda belirtilen veriler, yasal yükümlülükler dışında üçüncü 
kişi, kurum veya kuruluşlarla paylaşılmamakta; yurt içi veya yurt 
dışına aktarılmamaktadır.

ONAY VE KABUL
Bu aydınlatma metnini okuduğunuzu, anladığınızı ve yukarıda belirtilen 
kapsamda kişisel verilerinizin işlenmesine açık rıza verdiğinizi 
kabul ediyorsunuz.

SORUMLULUK SINIRLAMASI
Galeri erişimi ve görsel kullanımı tamamen sizin tercihinizdir. 
Seçtiğiniz görsellerin içeriğinden, telif haklarından veya üçüncü 
kişi haklarından siz sorumlusunuz. Uygulama geliştiricisi, kullanıcı 
tarafından seçilen görsellerden sorumlu tutulamaz.

DEĞİŞİKLİKLER
Bu metin, yasal düzenlemeler veya uygulama güncellemeleri nedeniyle 
değiştirilebilir. Önemli değişiklikler uygulama içi bildirimle 
duyurulur.

Son güncelleme: Şubat 2026
""".trimIndent()

@Composable
fun PrivacyScreen(
    showAcceptButton: Boolean,
    onAccept: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    var isChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "KVKK Aydınlatma Metni",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = privacyText,
                fontSize = 13.sp,
                color = AppColors.TextPrimary,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .fillMaxWidth(),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (showAcceptButton) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it },
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "KVKK aydınlatma metnini okudum ve kabul ediyorum",
                        fontSize = 13.sp,
                        color = AppColors.TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                
                MinimalButton(
                    text = "Tamam",
                    onClick = onAccept,
                    isPrimary = true,
                    enabled = isChecked
                )
            } else {
                Spacer(modifier = Modifier.height(12.dp))
                MinimalButton(
                    text = "Kapat",
                    onClick = onBack,
                    isPrimary = false
                )
            }
        }
    }
}

@Composable
private fun MinimalButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean,
    enabled: Boolean = true
) {
    val backgroundColor = if (isPrimary && enabled) AppColors.Primary else AppColors.Background
    val textColor = if (isPrimary && enabled) AppColors.TextOnPrimary else AppColors.Primary
    val borderColor = AppColors.Primary
    val alpha = if (enabled) 1f else 0.5f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(24.dp))
            .alpha(alpha)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            letterSpacing = 1.sp
        )
    }
}
