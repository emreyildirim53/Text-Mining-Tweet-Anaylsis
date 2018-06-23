# Metin Madenciliği Uygulamasıyla Tweet Verisinin Analiz Edilmesi

*Tweetter üzerinde iletişim sektörü hakkında atılmış 3000 adet tweetin olumlu, olumsuz ve nötür şeklinde ayrıştırılması.* 

>** Result** klasörü algoritmanın çalıştırılması sonucu elde edilmiştir.
> Aşağıdaki açıklamalar Result dosyasındaki program çıktılarını açıklamaktadır. 

**1-raw_texts :** Alınan ham veri

**2-raw_classes:** Ham veri içinden hash mantığıyla veriler sınıflarına ayrıştırıldı.

**3-splitword_classes:** Ayrıştırılan veriler arasındaki boşluklar ve bazı karakterler temizlendi.

**4-stop-words:** Veri üzerinde gereksiz kelime temizleme işlemi yapıldı.

**5-clear_classes:** Gereksiz kelime ve özel karakter temizliğinden sonra bir başlık altında veriler bir araya getirildi.

**6-uniqTF:** Veri setinde hangi kelimeden kaçar adet olduğunu görmek için kelimeler ve kelime frekansları bulundu. (İleriki adımlarda veri seti üzerinde bilgi sahibi olmak için bu işlem yapıldı.)

**7-stammer-and-spellcheck:** Temizlenen veriler "Zemberek" kütüphanesi yardımıyla köklerine ayrıldı ve sonrasında düzeltme işleminden geçirildi.

**8-uniqTFSpellCheckAndStammer:** Stammer ve spellcheck işlemi sonucunda kelimelerin frekanslarında oluşan değişimler gözlemlendi.

**9-TF-IDF:** Veri setindeki kelimelerin TF-IDF değerleri hesaplandı.

**10-Matual_Information**: Çıkarılan attributelerin projeye kazanımları tespit edildi.Tespit sonrasında bir takım trashold değerleriyle test edildi.

**11-TF-IDF_afterMatual_Information:** Trashold seviyesi altında kalan attributeler atıldıktan sonra TF-IDF hesaplaması tekrarlandı.

**12-createSample:** Sınıflandırma algoritması için k(10)-katmanlı cross-validation işlemi gerçekleştirildi.

**13-ResultValue:** Oluşturulan örneklem ve modeller sınıflandırma algoritmasında test edildi ve sonuç değerleri hesaplanıp dosyaya yazıldı.


* TF-IDF tablosu : 9-TF_IDF klasörü içerisinde matrixTF_IDF.csv tablosudur. Her bir trashold değeri içinde ayrıca TF-IDF tablosu oluşturulmuştur. Bu tablolar 11-TF_IDF_afterMatual_Information klasöründe yer almaktadır.

* Performans Ölçüm Değerleri : 13-ResultValue klasörü içerisinde her bir trashold değeri için oluşturulmuştur.(ResultValues.xlsx)
