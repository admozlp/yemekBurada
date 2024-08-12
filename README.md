# YemekBurada

## Proje Tanımı
YemekBurada, bir restoran için geliştirilen yemek sipariş uygulamasıdır. Projede temel nesneler olan Masa, Yemek ve Sipariş işlemleri tamamlanmıştır.

## Proje ile İlgili Notlar

### Configuration Management
- `application.properties`: Konfigürasyonlar Vault'a taşındı. Vault, konfigürasyonların tek bir yerden yönetilmesini ve güvenliğini sağlar. Ayrıca, konfigürasyon değişiklikleri için deploy işlemi yapmadan güncelleme yapmamıza olanak tanır.
- `actuator`'ın `/refresh` endpoint'i ile konfigürasyonlar güncellenebilir.
- `application.properties` dosyasında Vault konfigürasyonları için gerekli olan bilgiler bulunmaktadır. Bu bilgilerle Vault'a erişim sağlayabilir ve konfigürasyonları güncelleyebilirsiniz.

### Kotlin Kullanımı
- Lombok, compile time'da kod üreten bir kütüphanedir; proje büyüdükçe Lombok, build alma süresini uzatabilir. Kotlin ile Lombok'a gerek kalmadan getter, setter, equals, hashcode, toString gibi methodlar otomatik olarak oluşturulabilir.
- Veri tabanı nesneleri ve DTO'lar Kotlin `data class` ile yazıldı.

### Specification
- JPA'da method ismi ile sorgu oluşturmak yeterli olmadığında dinamik sorgular oluşturmak için `Specification` kullanıldı. Tarihe göre sipariş filtreleme ve yemek ismine göre filtreleme gibi işlemler için kullanıldı.
  - JPQL ve native query ile de sorgular yazılabilir.

### Unit Test
- Yemek Servisi (FoodService) için unit testler yazıldı.

### Veritabanı
- MySQL veritabanı uzak bir sunucuda bulunmaktadır. Proje direkt çalıştırılabilir. Vault'da bulunan bilgilerle veritabanına bağlantı sağlanabilir.

## Proje Yapısı
Aşağıda veri tabanı tasarımı bulunmaktadır.

![Veri Tabanı Tasarımı](https://github.com/admozlp/yemekBurada/blob/master/src/main/resources/static/db-design.png)

