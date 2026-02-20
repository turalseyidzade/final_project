package azcompany.final_projeckt.service.impl;

import azcompany.final_projeckt.client.BunnyClient;
import azcompany.final_projeckt.dao.entities.Book;
import azcompany.final_projeckt.dao.repositories.BookRepository;
import azcompany.final_projeckt.exceptions.EntityNotFoundException;
import azcompany.final_projeckt.service.BunnyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class BunnyServiceImpl implements BunnyService {
    private final BunnyClient bunnyClient;
    private final BookRepository bookRepository;

    @Value("${bunny.cdn.url}")
    private String bunnyCdnUrl;


    @Override
    public String uploadFile(Long bookId, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        log.info("ActionLog.uploadFile.started: bookId={}, fileName={}", bookId, fileName);

        try {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> {
                        log.error("ActionLog.uploadFile.error: Book not found with id={}", bookId);
                        return new EntityNotFoundException("Book not found with id: " + bookId);
                    });

            String response = bunnyClient.uploadFile(file, fileName);
            log.info("ActionLog.uploadFile.externalCallSuccess: fileName={}", fileName);

            String fileUrl = "https://tural-bookstore.b-cdn.net" + fileName;
            book.setCoverImage(fileUrl);
            bookRepository.save(book);
            log.info("ActionLog.uploadFile.end: bookId{} updated with cover image", bookId);
            return fileUrl;
        } catch (Exception ex) {
            log.error("ActionLog.uploadFile.error: Failed to upload file for bookId={}", bookId, ex);
            throw new EntityNotFoundException("Failed to upload file for bookId: " + bookId);
        }
    }
}
