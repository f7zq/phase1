# ATLAS — خطة التنفيذ التقنية

> الحالة: المرحلة 1 منفذة كملفات فعلية. المراحل 2–7 مخططة فقط.
> ملاحظة: لم يُجرَ أي بناء حتى الآن (لا يوجد Android SDK ولا شبكة في بيئة العمل). كل ما هو مكتوب "منفذ" يعني أن الكود مكتوب، وليس أنه جُرِّب على جهاز.

---

## 0. قرارات ومخاطر تحتاج موافقتك قبل المرحلة 2

### 0.1 مصادر YouTube / TikTok / Facebook (الأهم)
- YouTube لا يوفر API رسميًا لتنزيل الفيديو، وشروط خدمته تمنع التنزيل إلا عبر ميزة يوفرها YouTube نفسه. المكتبات غير الرسمية (NewPipeExtractor، yt-dlp وأمثالهما) تعتمد على فك حماية الروابط (signature cipher)، وهذا ما تمنعه تعليماتك في القسم 4 ("لا تتجاوز أنظمة الحماية").
- Facebook: لا يوجد API عام لتنزيل فيديوهات الآخرين.
- TikTok: الواجهات الرسمية (oEmbed / Embed) تعطي بيانات وصفية فقط، ولا تعطي ملف الفيديو. وخيار "بدون علامة مائية" يعتمد عادةً على خدمات طرف ثالث تتجاوز القيود، فلن يظهر إلا إذا وُجد مصدر مشروع يوفره.
- سياسات Google Play غالبًا ترفض التطبيقات التي تسهّل تنزيل محتوى هذه المنصات بما يخالف شروطها، كما أن سياسات ناشري Google (AdMob) تمنع عرض الإعلانات في تطبيقات تنتهك حقوق النشر. **راجع هذه السياسات بنفسك قبل النشر.**

**ما سأنفذه فعليًا (ويتوافق مع تعليماتك):**

| المصدر | ما سيعمل فعليًا | ما لن يعمل |
|---|---|---|
| روابط الملفات المباشرة (mp4/mp3/m4a/pdf/صور…) | كل شيء: تفاصيل، تحميل، استكمال، صوت فقط (استخراج من ملف مباشر) | — |
| مواقع مفتوحة الترخيص (Internet Archive، Wikimedia Commons…) | عبر مزود الملفات المباشرة | — |
| YouTube / TikTok / Facebook | عرض بيانات حقيقية عبر oEmbed الرسمي (عنوان، ناشر، صورة) | التنزيل: رسالة واضحة "هذا المصدر لا يسمح بالتنزيل من داخل ATLAS" |
| HLS غير المحمي (.m3u8) | مرحلة 3 اختياريًا عبر Media3 | المحتوى المحمي DRM |

إذا كان لديك اتفاق أو ترخيص أو API رسمي لمصدر معين، أخبرني وأضيف له مزودًا مستقلًا بدون تغيير بقية التطبيق (هذا سبب تصميم Source Providers).

### 0.2 صيغة MP3
لا يوجد مشفّر MP3 في Android نفسه، ومكتبة FFmpegKit الشهيرة أُوقف دعمها رسميًا. لذلك:
- M4A/AAC: نسخ المسار بدون إعادة ترميز (MediaExtractor + MediaMuxer) — جودة بلا فقدان.
- OPUS: نسخ المسار عندما يوفره المصدر.
- MP3: تُعرض فقط إذا كان المصدر نفسه ملف MP3. لن أعرضها كخيار تحويل إلا إذا اعتمدنا مكتبة تشفير موثوقة (قرار منفصل بمرحلة 3).
- هذا يطابق قاعدتك: "لا تعرض صيغة على أنها مدعومة ما لم يكن التطبيق قادرًا فعليًا على إنتاجها".

### 0.3 معرّف الحزمة (applicationId)
تطبيق ATLAS في AdMob قد يكون مرتبطًا باسم حزمة محدد. أضع القيمة مؤقتًا `com.atlas.downloader` في `gradle.properties` (`atlas.applicationId`). **أرسل لي اسم الحزمة الفعلي** إن كان مرتبطًا. نسخة Debug تضيف `.debug` وتستخدم معرّفات اختبار فقط.

### 0.4 إصدارات المكتبات و targetSdk
كتبت إصدارات متحفظة (AGP 8.7.3، Kotlin 2.0.21، Compose BOM 2024.12.01، compileSdk/targetSdk 35) لأنني لا أستطيع التحقق من الأحدث بدون شبكة. اليوم 24 نوفمبر 2026، وقد يكون Google Play يشترط targetSdk أعلى للتطبيقات الجديدة. **حدّث الإصدارات وtargetSdk قبل أول بناء حقيقي.**

### 0.5 تأثير Blur الحقيقي
Compose لا يوفر blur للخلفية خلف العنصر (backdrop blur) بشكل مباشر. المرحلة 1 تحاكي الزجاج بتدرجات شفافة وحدود لامعة وإضاءة ذهبية فوق خلفية متحركة. لاحقًا يمكن اعتماد مكتبة Haze لـ blur حقيقي على Android 12+ مع بديل للأجهزة الأقدم.

---

## 1. هيكل ملفات المشروع

```
ATLAS/
├── settings.gradle.kts · build.gradle.kts · gradle.properties
├── gradle/libs.versions.toml
├── docs/ATLAS_PLAN.md
└── app/
    ├── build.gradle.kts            ← buildTypes: debug=معرّفات اختبار، release=معرّفات الإنتاج
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml
        ├── res/  (values · values-ar · xml/locales_config · drawable · mipmap-anydpi-v26)
        └── kotlin/com/atlas/downloader/
            ├── AtlasApp.kt · MainActivity.kt
            ├── ads/            AdsConfig · AdsManager (+NoOp)           [المرحلة 6: الباقي]
            ├── data/settings/  SettingsRepository (DataStore)
            ├── ui/theme/       AtlasColors · Theme
            ├── ui/components/  AtlasBackground · GlassSurface · GlassChoiceChip · GlassBottomBar · …
            ├── ui/navigation/  Routes · TopLevel
            ├── ui/screens/     splash · home · downloads · library · more · settings · about
            └── ui/AtlasRoot.kt
```

الحزم التي تُضاف في المراحل التالية (كلها خارج طبقة الواجهة):
```
domain/    models + use-cases (ResolveLink, EnqueueDownload, ...)
data/db/   Room: entities + DAOs + AtlasDatabase
download/  engine/ (HttpDownloader, RateLimiter, RangeResume)
           queue/  (SmartQueue, DownloadCoordinator)
           service/ (DownloadService foreground)
           audio/  (AudioExtractor, AudioFormatPolicy)
source/    SourceProvider + providers/ (DirectFile, OEmbedMetadata, …)
playlist/  PlaylistManager
media/     library/ player/ subtitles/ capture/
browser/   WebView wrapper
privacy/   PrivateFolder (Biometric + PIN hash)
ads/       Banner/Interstitial/Native/AppOpen managers · FrequencyController · Consent
```

## 2. الشاشات

| # | الشاشة | المرحلة |
|---|---|---|
| 1 | Splash | 1 ✅ |
| 2 | Onboarding (اختياري) | 7 إن رغبت |
| 3 | Home | 1 (هيكل) → 2 (الوظائف) |
| 4–5 | Link Details + Download Options | 2 |
| 6–7 | Downloads + Download Details | 2 |
| 8 | Playlist Downloader | 3 |
| 9–10 | Media Library + Player | 4 |
| 11 | Browser | 5 |
| 12 | Settings | 1 (لغة + مظهر) → كل مرحلة تضيف قسمها |
| 13 | About | 1 ✅ |

القائمة السفلية: Home · Downloads · Library · More. كل ما هو غير منفذ يظهر عليه وسم "قريبًا" بدون أزرار تدّعي العمل.

## 3. قاعدة البيانات المحلية (Room)

- **downloads**: id، sourceUrl، providerId، title، uploader، siteName، thumbnailUrl، kind (VIDEO / AUDIO_ONLY / FILE)، container، qualityLabel، status (QUEUED/RUNNING/PAUSED/RETRY_WAIT/COMPLETED/FAILED/CANCELED)، priority، queueOrder، playlistId?، playlistIndex?، totalBytes?، downloadedBytes، supportsRange، etag/lastModified، tempPath، finalUri، mimeType، durationMs?، errorCode، retryCount، nextRetryAt، createdAt، completedAt.
- **playlists**: id، sourceUrl، title، thumbnailUrl، itemCount، createdAt.
- **media_items** (فهرس المكتبة): id، uri، displayName، mediaType (VIDEO/AUDIO/IMAGE/OTHER)، sizeBytes، durationMs، format، sourceTitle، sourceName، downloadId?، isPrivate، addedAt. يُتحقق عند كل فتح من وجود الملف الفعلي، ويُحذف السجل إن اختفى الملف.
- **subtitles**: mediaId، uri، language?، offsetMs.
- **DataStore**: اللغة والمظهر وكل الإعدادات (سرعة قصوى، تزامن، Wi-Fi فقط، سرعة التشغيل، Smart Link…).
- **PIN**: لا يُخزَّن نصًا؛ PBKDF2 + salt، والمفتاح مغلّف عبر Android Keystore. البصمة عبر BiometricPrompt.

## 4. معمارية محرك التحميل

- **HttpDownloader** (OkHttp): GET مع `Range` + `If-Range` (ETag/Last-Modified) للاستكمال، ملف `.part` ثم نقل ذري للاسم النهائي. إن لم يدعم الخادم Range يُعاد من الصفر ويُعلَن ذلك للمستخدم.
- **RateLimiter** (token bucket عام مشترك بين المهام): يلفّ الـ InputStream فعليًا فيحدد معدل القراءة، فيكون الحد حقيقيًا وليس رقمًا معروضًا.
- **SmartQueue**: يقرأ من Room، يحترم priority/queueOrder وحد التزامن، ويدعم Pause/Resume/Cancel/Retry وإعادة الترتيب. التخطي: مقارنة (sourceUrl + variant) أو الاسم + الحجم مع ملف موجود.
- **Retry**: backoff قابل للضبط (عدد المحاولات، الفاصل)، يميّز الأخطاء القابلة لإعادة المحاولة (انقطاع، 5xx، 429) عن النهائية (404، 403، قرص ممتلئ، مصدر غير مدعوم). مراقبة الشبكة عبر ConnectivityManager، وقيد Wi-Fi فقط.
- **الاستضافة**: Foreground Service بنوع `dataSync` مع إشعار تقدم. على Android 14+ توجد قيود/مدد على هذا النوع (وخصوصًا Android 15) وبديل User-Initiated Data Transfer jobs؛ سأتحقق من الوثائق الحالية قبل المرحلة 2 وأختار الأنسب. أي إعلان لا يمر عبر هذا الطبقة إطلاقًا.
- **التخزين**: MediaStore (Downloads/Movies/Music) افتراضيًا، أو مجلد يختاره المستخدم عبر Storage Access Framework. لا أذونات تخزين واسعة على Android 10+.
- الواجهة تراقب Flow من Room فقط، فلا يتجمد أي شيء أثناء التحميل.

## 5. دعم المصادر (Modular Source Providers)

```kotlin
interface SourceProvider {
    val id: String
    fun canHandle(url: HttpUrl): Boolean
    suspend fun resolve(url: HttpUrl): ResolveResult
}
sealed interface ResolveResult {
    data class Media(val info: MediaInfo) : ResolveResult
    data class Playlist(val info: PlaylistInfo) : ResolveResult
    data class Unsupported(val reason: UnsupportedReason) : ResolveResult  // رسالة واضحة، لا نجاح وهمي
    data class Failure(val error: Throwable) : ResolveResult
}
```
- `MediaInfo`: title، uploader، thumbnail، durationMs?، siteName، `variants: List<Variant>`، لكل Variant: النوع (فيديو+صوت / صوت فقط)، الحاوية، الجودة، الحجم؟، supportsRange، وسيلة الجلب.
- المزودون: `DirectFileProvider` (HEAD → Content-Type/Length/Disposition)، `OEmbedMetadataProvider` (YouTube/TikTok رسميًا: بيانات فقط، `Unsupported` للتنزيل)، لاحقًا أي مزود مرخّص.
- لا تُعرض حقول فارغة بقيم وهمية: ما لم يُستخرج يُخفى.

## 6. تحميل الصوت فقط

1. المزود يعلن `AUDIO_ONLY` فقط إذا وُجد فعلًا: (أ) مسار صوتي مستقل مرخّص، أو (ب) ملف فيديو مباشر يمكن استخراج صوته. وإلا يُخفى الخيار ويظهر سبب واضح عند الطلب.
2. حالة (أ): تنزيل المسار الصوتي مباشرة بدون بيانات الفيديو.
3. حالة (ب): تنزيل الملف ثم `AudioExtractor` يستخرج المسار الكامل إلى `.m4a`/`.opus` بدون إعادة ترميز (MediaExtractor + MediaMuxer) ويحذف الملف المؤقت. استهلاك الذاكرة ثابت (بث على كتل). لا قص ولا مقاطع.
4. المهمة تدخل نفس SmartQueue ولها حالة مرحلتين (تنزيل ← استخراج) تظهر في الواجهة.
5. `AudioFormatPolicy` يحسب الصيغ المتاحة لكل مصدر؛ الواجهة تعرض فقط ما يعيده.

## 7. إدارة ملفات الصوت الناتجة

- يُسجَّل في `media_items` بالحقول: اسم الملف، عنوان الفيديو الأصلي، المصدر، المدة، الحجم، الصيغة، تاريخ التحميل.
- يظهر في قسم Audio بالمكتبة، ويُشغَّل بالمشغل الداخلي (Media3)، ويدعم قائمة التشغيل السابق/التالي والسرعات.
- الحذف بموافقة المستخدم فقط، والنقل للمجلد الخاص ينقل الملف لتخزين التطبيق الداخلي.

## 8. إدارة الإعلانات

`AdsManager` واجهة واحدة تخفي كل التفاصيل عن بقية التطبيق:
- `AdConsentManager` (UMP) ← يجب أن ينتهي قبل أي طلب إعلان.
- `AdFrequencyController`: يخزّن آخر ظهور لكل نوع، وعدد مرات الجلسة، وتاريخ أول تشغيل (DataStore).
- `AdLifecycleObserver` (ProcessLifecycleOwner): يتتبع foreground/background.
- `AdsPolicy.canShowFullScreen()` مركزية: ترفض إذا (يوجد تحميل نشط) أو (تشغيل وسائط) أو (إعلان ملء شاشة معروض) أو (لم تمضِ الفترة الفاصلة) أو (الجلسة الأولى).
- `BannerAdManager` (Adaptive)، `NativeAdManager`، `InterstitialAdManager`، `AppOpenAdManager` — كل منها يعالج: نجاح، فشل، No Fill، انتهاء الصلاحية، تحرير الموارد.
- فشل أي إعلان لا يلمس بقية التطبيق: كل النتائج حالات `Flow` تتجاهلها الواجهة.

## 9. أماكن الإعلانات الأربعة

| النوع | المكان | الشروط |
|---|---|---|
| Banner | أسفل Home فوق القائمة السفلية، مساحة محجوزة بارتفاع ثابت | Home فقط؛ لا فوق Download/Pause/Resume؛ لا تغيير تخطيط عند التحميل |
| Native | عنصر واحد داخل قائمة Media Library (بعد العنصر السادس، وفقط إن كان في القائمة 8 عناصر فأكثر) | وسم "إعلان/Sponsored" واضح، تباعد مميز، AdChoices، لا يشبه ملفًا، واحد لكل شاشة |
| Interstitial | عند الخروج من المشغل/تفاصيل ملف إلى المكتبة (انتقال طبيعي) | افتراضيًا: فاصل ≥ 5 دقائق، حد أقصى 3 في الجلسة، لا أول جلستين، ولا مع أي تحميل نشط أو تشغيل |
| App Open | عند العودة من الخلفية | افتراضيًا ≥ 4 ساعات بين مرتين، لا بعد التثبيت مباشرة، لا خلال دقيقة من Interstitial، لا أثناء تحميل/تشغيل، وعمر الإعلان المحمّل ≤ 4 ساعات |

كل الأرقام قابلة للتعديل من `AdsPolicyConfig` (قيم افتراضية محافظة).

## 10. فصل معرّفات الاختبار والإنتاج

منفذ فعليًا في `app/build.gradle.kts`:
- `debug`: `buildConfigField` + `manifestPlaceholders["admobAppId"]` بمعرّفات Google الرسمية التجريبية.
- `release`: معرّفاتك الفعلية كما هي حرفيًا.
- الكود يقرأ فقط من `AdsConfig` (يقرأ `BuildConfig`)، فلا معرّف إنتاج في أي كود آخر.
- في المرحلة 6: مهمة Gradle تفشل إن ظهرت معرّفات `3940256099942544` في بناء release.
- Test Devices عبر `RequestConfiguration` في debug فقط.

## 11. مراحل التنفيذ والاختبار

| المرحلة | المحتوى | اختبار القبول |
|---|---|---|
| 1 ✅ كود جاهز | أساس + ثيم + Splash + Home + Navigation + Settings (لغة/مظهر) + About + مكان AdsManager | يبنى؛ التنقل يعمل؛ تبديل AR/EN يقلب الاتجاه RTL/LTR؛ المظهر يُحفظ بعد إعادة التشغيل |
| 2 | Share Intent + DirectFileProvider + oEmbed + Link Details + Download Manager + Downloads | مشاركة رابط ملف مباشر من متصفح؛ تنزيل حقيقي مع تقدم وإيقاف واستكمال |
| 3 | Smart Queue + حد السرعة + Retry + Playlist + صوت فقط داخل القوائم | قياس السرعة الفعلية مقابل الحد؛ فصل الشبكة أثناء التحميل |
| 4 | Library + Player + ترجمة + سرعة التشغيل + Capture Frame | ملفات حقيقية من الجهاز؛ SRT/VTT |
| 5 | Browser + Smart Link + إدارة التخزين + Private Folder | بصمة/PIN؛ لا مراقبة حافظة بالخلفية |
| 6 | AdMob كامل + UMP | اختبار بمعرّفات تجريبية على جهاز فعلي؛ محاكاة No Fill |
| 7 | إصلاحات + أداء + سياسة الخصوصية + APK/AAB | مراجعة سياسات AdMob وPlay |

لا أنتقل لمرحلة قبل أن تجرّب أنت مرحلة الاختبار على جهازك وتؤكد.

---

## 12. تحديث القرارات المعتمدة (24 نوفمبر 2026)

> هذا القسم **يحل محل** جدول المراحل في القسم 11 وأي تعارض مع الأقسام السابقة.

### 12.1 القرارات
- معتمد: استخراج المسار الصوتي الكامل، الترجمة SRT/VTT، سرعة التشغيل، التقاط إطار، Private Folder، **QR Transfer المحلي**، مصادر التحميل المرخصة.
- ملغي: قص الصوت، قص الفيديو، تدوير الفيديو، Cloud Sync، Remote Download، Premium والاشتراكات.
- YouTube/TikTok/Facebook: بيانات وصفية رسمية فقط، بدون تنزيل فعلي إلا بطريقة رسمية أو مرخصة. TikTok بدون علامة مائية لا يظهر إلا بنسخة نظيفة مشروعة.
- QR Transfer كان مستبعدًا في الوثيقة الأولى، وهو الآن معتمد بقرارك الأحدث.

### 12.2 ترتيب المراحل الجديد

| المرحلة | المحتوى |
|---|---|
| 1 | الأساس والهوية والتنقل والإعدادات (كود مكتوب، لم يُبنَ ولم يُختبر) |
| 2 | استقبال الروابط، Source Providers، تفاصيل المحتوى، Download Manager |
| 3 | Smart Queue، سرعة التحميل، Retry، Playlist Downloader، Audio Extraction |
| 4 | Media Library، المشغل، الترجمة، سرعة التشغيل، التقاط إطار |
| 5 | Browser، Smart Link Detection، إدارة التخزين، Private Folder |
| 6 | QR Transfer المحلي والتوافق مع ATLAS Desktop |
| 7 | AdMob، الاختبارات النهائية، الأمان، الأداء، APK/AAB |

AdMob أصبح في المرحلة 7. حزمة `ads/` الحالية (AdsConfig + NoOpAdsManager) تبقى كما هي حتى ذلك الحين، ومعرّفات debug/release جاهزة في Gradle.

### 12.3 شرط القبول (ملزم)
لا تُعتبر ميزة منفذة إلا بعد: كتابة الكود ← نجاح Android Build ← تشغيل على جهاز أو محاكي ← اختبار الوظيفة ← معالجة الأخطاء ← موافقتك. لا انتقال لمرحلة قبل الموافقة على السابقة.
**الوضع الحالي: المرحلة 1 في خطوة "كود مكتوب" فقط.**

### 12.4 AudioExtractor (`download/audio/AudioExtractor.kt`)
- يقرأ المسار الصوتي بـ MediaExtractor، ويكتبه بـ MediaMuxer (نسخ بدون إعادة ترميز) إلى ملف مستقل، ثم يتحقق من النتيجة (وجود المسار، المدة تطابق الأصل ضمن هامش صغير، الملف قابل للفتح) قبل اعتبار العملية ناجحة.
- الكتابة إلى ملف مؤقت `.tmp` ثم نقل ذري للاسم النهائي؛ التنظيف عند النجاح والفشل والإلغاء (`try/finally`).
- يرسل حالة (قيد الاستخراج / نجح / فشل + السبب) إلى Download Manager.
- الصيغ المعروضة تحددها `AudioFormatPolicy` بعد فحص الحاوية والترميز فعليًا: M4A/AAC و OPUS حسب التوافق مع حاوية MediaMuxer. MP3 فقط إذا كان المسار نفسه MP3؛ لا مكتبة ترميز معتمدة حاليًا، فلا يظهر تحويل إلى MP3.
- لا قص، لا تحرير، لا DRM.

### 12.5 Private Folder (`privacy/`)
المكونات: `PrivateFolderManager`، `FileEncryptionManager`، `BiometricAuthManager`، `PinManager`.
- **التشفير**: ملفات الفيديو كبيرة، فلا يصلح AES-GCM دفعة واحدة. سأستخدم تشفيرًا متدفقًا مقسّمًا إلى كتل (مثل Tink Streaming AEAD) مع مفتاح محفوظ في Android Keystore؛ والكتل تسمح بالتقديم والتأخير داخل المشغل عبر `DataSource` مخصص يفك التشفير أثناء القراءة. (أتحقق من المكتبة وإصدارها وقت التنفيذ.)
- **PIN**: اشتقاق مفتاح بـ PBKDF2/Argon2 مع salt، ولا يُخزَّن نصًا. محاولات فاشلة متتابعة تُبطَّأ.
- **النقل الآمن**: سجل عمليات (journal) في Room: نسخ مشفّر ← التحقق بفك التشفير ومقارنة hash للأصل ← تحديث الفهرس ← حذف الأصل **بعد** موافقتك. عند انقطاع التطبيق تُستأنف أو تُتراجع العملية عند الفتح التالي، فلا يضيع ملف.
- الملفات الخاصة لا تظهر في المكتبة العامة (`isPrivate = true`)، وتُحدَّث الفهرسة.

### 12.6 Media Library
- حقول الفهرس تشمل: File Name، Original Video Title، Source Name، Duration، File Size، Audio Format، Created Date، Download ID، Private Status.
- التبويبات: Videos / Audio / Images / **Private Files** (محمي بالبصمة أو PIN). أفترض بقاء "All Files" كعرض إضافي إلا إذا طلبت حذفه.

### 12.7 مزودو المصادر المرخصة
- واجهة `SourceProvider` تضيف: `supportsResume`، `hasStandaloneAudio`، `canExtractAudioFromVideo`، `unsupportedReason`.
- كل مزود خارجي يُوثَّق له ملف ترخيص/إذن (`providers/LICENSES.md`) يذكر مصدر الإذن وشروطه.
- إضافة مزود جديد لا تمس محرك التحميل.

### 12.8 QR Transfer المحلي (مرحلة 6)
- نقل على الشبكة المحلية بين الهاتف وATLAS Desktop، بدون إنترنت خارجي.
- **يحتاج مني مواصفات ATLAS Desktop**: بروتوكول النقل، المنفذ، شكل بيانات الـ QR، طريقة الاقتران والتشفير. بدونها لن أستطيع ضمان التوافق.
- تصميم مقترح للأمان: الـ QR يحمل عنوان الجهاز + منفذ + رمز اقتران لمرة واحدة + بصمة الشهادة؛ اتصال TLS مشفّر؛ التحقق من سلامة الملف بـ SHA-256؛ يُعرض للمستخدم طلب قبول قبل أي نقل وارد.
- أذونات: كاميرا لمسح QR فقط عند استخدام الميزة، وصول للشبكة المحلية.

