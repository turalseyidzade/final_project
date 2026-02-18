package azcompany.final_projeckt.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BunnyClient {
    private final RestTemplate restTemplate;

    public String uploadFile(MultipartFile file, String fileName) throws IOException {

        var folder = "matrix_167";
        var baseUrl = "https://storage.bunnycdn.com/turalseyidzade/" + fileName + "/" + folder;

        var headers = new HttpHeaders();
        headers.put("AccessKey", List.of("5a24c333-69d6-4e39-9bfd31a873b1-9e47-4c6c"));

        if (file.getContentType() != null) {
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.inline().filename(fileName).build());
        }
        HttpEntity<byte[]> request = new HttpEntity<>(file.getInputStream().readAllBytes(), headers);
        var response = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Void.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IOException("Upload failed: " + response.getStatusCode());
        }
        return "https://turalseyidzade.b-cdn.net/" + folder + "/" + fileName;

    }
    public Resource downloadFile(String fileName) throws IOException {
        var folder = "matrix_167";

        return new FileSystemResource(folder + "/" + fileName);
    }



}
