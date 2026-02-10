## Refleksi 1: 

Dokumen ini menjelaskan prinsip clean code dan praktik keamanan yang saya terapkan saat menambahkan fitur "edit product" dan "delete product" pada proyek Spring Boot sederhana ini. Juga berisi temuan kode yang perlu diperbaiki dan saran peningkatan.

## Ringkasan perubahan
- `ProductRepository` : menambahkan metode pencarian (`findById`), pembaruan (`update`) dan penghapusan (`deleteById`).
- `ProductService` dan `ProductServiceImpl` : menambahkan abstraksi/implementasi untuk mencari, memperbarui, menghapus; `create` kini menghasilkan UUID bila `productId` kosong.
- `ProductController` : menambahkan endpoint untuk edit, update, dan delete. Mengganti redirect relatif ke redirect absolut (`redirect:/product/list`) untuk menghindari loop redirect.
- Template Thymeleaf: menambahkan `EditProduct.html`; mengubah `ProductList.html` untuk menampilkan tombol Edit dan Delete; menambahkan konfirmasi JS sederhana pada tombol Delete.

## Clean code principles yang diterapkan
Berikut prinsip-prinsip clean code yang saya terapkan dan di mana mereka muncul di kode:

- Single Responsibility Principle
  - `ProductRepository` hanya bertanggung jawab atas penyimpanan/in-memory CRUD sederhana.
  - `ProductService` mengenkapsulasi logika aplikasi (pemetaan/ID generation, orkestrasi repository).
  - `ProductController` hanya menangani routing dan mempersiapkan model untuk view.

- Separation of Concerns / Layered Architecture
  - Memisahkan controller, service, dan repository sehingga masing-masing lapisan punya tanggung jawab jelas.

- Meaningful Names
  - Nama method dan variabel (mis. `findById`, `deleteById`, `productRepository`) mudah dimengerti.

- Keep methods small and focused
  - Metode di service dan repository kecil dan melakukan satu aksi.

- Defensive programming (parsial)
  - Menambahkan pemeriksaan null sederhana pada repository (`deleteById`, `findById`) untuk menghindari NPE.

- Fail-fast untuk ID generation
  - `create` menghasilkan UUID bila ID kosong sehingga entitas selalu punya identifier untuk operasi selanjutnya.

## Praktik secure coding yang diterapkan
- Hindari infinite redirect loop
  - Mengubah redirect relatif `redirect:list` menjadi `redirect:/product/list` sehingga URL target jelas dan tidak diperlakukan sebagai path relatif yang menyebabkan :id menjadi `list` dan memicu mapping delete lagi.

- Konfirmasi penghapusan di client
  - Menambahkan `onclick="return confirm('Delete this product?');"` untuk mengurangi penghapusan tidak sengaja.

- Output escaping
  - Thymeleaf secara default melakukan escaping untuk ekspresi `${...}` sehingga membantu mencegah XSS pada tampilan.

## Kekurangan / masalah yang ditemukan dan cara memperbaiki
Berikut beberapa kelemahan praktis dari implementasi saat ini dan rekomendasi perbaikan:

1) Delete menggunakan HTTP GET (unsafe)
   - Masalah: Endpoint `GET /product/delete/{id}` melakukan aksi destruktif. Ini melanggar prinsip REST dan memungkinkan CSRF atau link-unintended deletion.
   - Perbaikan: Ubah menjadi POST (atau DELETE) dan gunakan CSRF token yang disediakan Spring Security. Di tampilan, ganti link dengan form kecil:

     <form th:action="@{/product/delete/{id}(id=${product.productId})}" method="post" style="display:inline">
         <input type="hidden" name="_csrf" th:value="${_csrf.token}" />
         <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Delete this product?');">Delete</button>
     </form>

   - Dan ubah controller menjadi @PostMapping("/delete/{id}") atau @DeleteMapping.

2) Input validation hilang
   - Masalah: Binding form ke `Product` tanpa validasi dapat menyebabkan exception (mis. field kuantitas kosong -> NumberFormatException) dan data invalid tersimpan.
   - Perbaikan: Tambahkan validasi menggunakan bean validation (Jakarta Validation):
     - Tambahkan anotasi di model: `@NotBlank` pada `productName`, `@Min(0)` pada `productQuantity` (ubah tipe ke `Integer` untuk menangani null input dengan baik).
     - Di controller, terima `@Valid @ModelAttribute Product product, BindingResult result` dan tampilkan kembali form jika ada error.

3) Tipe `int` pada `productQuantity`
   - Masalah: `int` tidak menerima null; saat form dikirim tanpa value akan menimbulkan error parsing.
   - Perbaikan: Gunakan `Integer` untuk menerima null dan lakukan validasi eksplisit.

4) Thread-safety dan concurrency
   - Masalah: `ProductRepository` menggunakan `ArrayList` non-sinkron; bila aplikasi berjalan multi-threaded (server nyata) bisa terjadi kondisi race saat create/update/delete.
   - Perbaikan: Gunakan `CopyOnWriteArrayList`, `Collections.synchronizedList(...)`, atau lebih baik gunakan database (H2, PostgreSQL, dsb.) untuk persistence.

5) Tidak ada logging dan error handling
   - Masalah: Kegagalan atau kondisi edge tidak dilaporkan.
   - Perbaikan: Tambahkan logging (SLF4J) pada service/controller/repository dan gunakan mekanisme exception handler (ControllerAdvice) untuk menampilkan pesan kesalahan yang ramah pengguna.

6) Tidak ada unit/integration tests
   - Perbaikan: Tambahkan unit tests untuk service/repository dan integrasi MVC tests untuk controller (MockMvc) untuk menjamin perilaku CRUD.

7) Potential XSS and output encoding
   - Keterangan: Thymeleaf melakukan escaping default, namun bila ada `th:utext` atau output yang tidak di-escape, perlu diwaspadai.

8) No authentication/authorization
   - Saran: Jika aplikasi akan digunakan nyata, tambahkan Spring Security dan batasi akses ke operasi create/edit/delete.

## Rekomendasi refactor kecil (contoh)
- Ubah `Product.productQuantity` menjadi `Integer` dan tambahkan validasi:

```java
@NotBlank
private String productName;
@NotNull
@Min(0)
private Integer productQuantity;
```

- Ubah delete menjadi POST seperti dijelaskan di atas.
- Tambahkan `BindingResult` handling pada controller untuk create/update.
- Tambahkan logging statements, contoh: `private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);` dan log pada operasi penting.

## Keamanan tambahan bila di-deploy nyata
- Aktifkan HTTPS
- Gunakan CSRF protection dan method POST/DELETE untuk operasi destruktif
- Validasi dan sanitasi semua input
- Batasi ukuran input dan rate-limit bila perlu

## Penutup
Secara umum, perubahan-perubahan yang dilakukan mengikuti pola layer-service-repository dan prinsip clean-code dasar. Namun implementasi ini masih minimal (in-memory, tidak ada validasi, tidak ada proteksi autentikasi/otorisasi). Untuk produksi, langkah-langkah perbaikan yang saya sebutkan harus diterapkan.
