package azcompany.final_projeckt.controller;

import azcompany.final_projeckt.service.BunnyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final BunnyService bunnyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String uploadFile(@RequestPart MultipartFile file) {
        return bunnyService.uploadFile(file);
    }
}
