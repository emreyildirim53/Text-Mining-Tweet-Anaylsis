# Metin Madenciliği Uygulamasıyla Tweet Verisinin Analiz Edilmesi

*Tweetter üzerinde iletişim sektörü hakkında atılmış 3000 adet tweetin olumlu, olumsuz ve nötür şeklinde ayrıştırılması.* 

>** Result** klasörü algoritmanın çalıştırılması sonucu elde edilmiştir.
> Aşağıdaki açıklamalar Result dosyasındaki program çıktılarını açıklamaktadır. 

**1-raw_texts : **Alınan ham veri
**2-raw_classes:** Ham veri içinden hash mantığıyla veriler sınıflarına ayrıştırıldı.
**3-splitword_classes:
4-stop-words: 
5-clear_classes:
6-uniqTF:
7-stammer-and-spellcheck: 
8-uniqTFSpellCheckAndStammer:
9-TF-IDF:
10-Matual_Information:
11-TF-IDF_afterMatual_Information:** Ayrıştırılan veriler arasındaki boşluklar ve bazı karakterler temizlendi.
**3-splitword_classes:
4-stop-words: 
5-clear_classes:
6-uniqTF:
7-stammer-and-spellcheck: 
8-uniqTFSpellCheckAndStammer:
9-TF-IDF:
10-Matual_Information:
11-TF-IDF_afterMatual_Information:**Veri üzerinde gereksiz kelime temizleme işlemi yapıldı.
**3-splitword_classes:
4-stop-words: 
5-clear_classes:
6-uniqTF:
7-stammer-and-spellcheck: 
8-uniqTFSpellCheckAndStammer:
9-TF-IDF:
10-Matual_Information:
11-TF-IDF_afterMatual_Information:** Gereksiz kelime ve özel karakter temizliğinden sonra bir başlık altında veriler bir araya getirildi.
**3-splitword_classes:
4-stop-words: 
5-clear_classes:
6-uniqTF:
7-stammer-and-spellcheck: 
8-uniqTFSpellCheckAndStammer:
9-TF-IDF:
10-Matual_Information:
11-TF-IDF_afterMatual_Information:** Veri setinde hangi kelimeden kaçar adet olduğunu görmek için kelimeler ve kelime frekansları bulundu. (İleriki adımlarda veri seti üzerinde bilgi sahibi olmak için bu işlem yapıldı.)
**3-splitword_classes:
4-stop-words: 
5-clear_classes:
6-uniqTF:
7-stammer-and-spellcheck: 
8-uniqTFSpellCheckAndStammer:
9-TF-IDF:
10-Matual_Information:
11-TF-IDF_afterMatual_Information:**Temizlenen veriler "Zemberek" kütüphanesi yardımıyla köklerine ayrıldı ve sonrasında düzeltme işleminden geçirildi.
**3-splitword_classes:
4-stop-words: 
5-clear_classes:
6-uniqTF:
7-stammer-and-spellcheck: 
8-uniqTFSpellCheckAndStammer:
9-TF-IDF:
10-Matual_Information:
11-TF-IDF_afterMatual_Information:** Stammer ve spellcheck işlemi sonucunda kelimelerin frekanslarında oluşan değişimler gözlemlendi.
**3-splitword_classes:
4-stop-words: 
5-clear_classes:
6-uniqTF:
7-stammer-and-spellcheck: 
8-uniqTFSpellCheckAndStammer:
9-TF-IDF:
10-Matual_Information:
11-TF-IDF_afterMatual_Information:** Veri setindeki kelimelerin TF-IDF değerleri hesaplandı.
**3-splitword_classes:
4-stop-words: 
5-clear_classes:
6-uniqTF:
7-stammer-and-spellcheck: 
8-uniqTFSpellCheckAndStammer:
9-TF-IDF:
10-Matual_Information:
11-TF-IDF_afterMatual_Information:** Çıkarılan attributelerin projeye kazanımları tespit edildi.Tespit sonrasında bir takım trashold değerleriyle test edildi.
**3-splitword_classes:
4-stop-words: 
5-clear_classes:
6-uniqTF:
7-stammer-and-spellcheck: 
8-uniqTFSpellCheckAndStammer:
9-TF-IDF:
10-Matual_Information:
11-TF-IDF_afterMatual_Information:** Trashold seviyesi altında kalan attributeler atıldıktan sonra TF-IDF hesaplaması tekrarlandı.
**12-createSample:
13-ResultValue:** Sınıflandırma algoritması için k(10)-katmanlı cross-validation işlemi gerçekleştirildi.
**12-createSample:
13-ResultValue:** Oluşturulan örneklem ve modeller sınıflandırma algoritmasında test edildi ve sonuç değerleri hesaplanıp dosyaya yazıldı.

* TF-IDF tablosu : 9-TF_IDF klasörü içerisinde matrixTF_IDF.csv tablosudur. Her bir trashold değeri içinde ayrıca TF-IDF tablosu oluşturulmuştur. Bu tablolar 11-TF_IDF_afterMatual_Information klasöründe yer almaktadır.

* Performans Ölçüm Değerleri : 13-ResultValue klasörü içerisinde her bir trashold değeri için oluşturulmuştur.(ResultValues.xlsx)
