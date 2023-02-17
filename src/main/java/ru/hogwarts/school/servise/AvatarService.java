package ru.hogwarts.school.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("{Avatar.cover.dir.path}")
    private String coversDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public void uploadCover(Long id, MultipartFile avatarFile) throws IOException {
        Student student = studentService.getStudent(id);

        Path filePath = Path.of(coversDir, id + "." + getExtension(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(id);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long id) {
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public Collection<Avatar> getAvatarPage(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    public Integer sum4you() {
        long start = System.currentTimeMillis();
        int sum1 = 0;
        for (int i = 0; i < 100_000_000; i++) {
               sum1+=i;
        }
//        int sum = Stream.iterate(1, a -> a + 1)
//          //      .parallel()
//                .limit(100_000_000)
//                .mapToInt(Integer::intValue)
//                .sum();
        long timeRequest = System.currentTimeMillis() - start;
        logger.info("time = " + timeRequest + " ms");
        return sum1;
    }
}
