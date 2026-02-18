package azcompany.final_projeckt.service.impl;

import azcompany.final_projeckt.client.BunnyClient;
import azcompany.final_projeckt.service.BunnyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class BunnyServiceImpl implements BunnyService {
    private final BunnyClient bunnyClient;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            log.info("ActionLog.uploadFile.started fileName={}", file.getOriginalFilename());
            var response = bunnyClient.uploadFile(file,file.getOriginalFilename());
            log.info("ActionLog.uploadFile.end fileName={}", file.getOriginalFilename());
            return response;
        }catch (Exception ex){
            log.error("Filed to upload file to BunnyCDN", ex);
            throw new RuntimeException("Filed to upload file to BunnyCDN");
        }
    }
}
