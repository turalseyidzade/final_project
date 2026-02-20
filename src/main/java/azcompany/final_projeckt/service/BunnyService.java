package azcompany.final_projeckt.service;

import org.springframework.web.multipart.MultipartFile;

public interface BunnyService {
    String uploadFile(Long bookId, MultipartFile file);
}
