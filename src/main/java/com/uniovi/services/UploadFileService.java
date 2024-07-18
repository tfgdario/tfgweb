package com.uniovi.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

	private String upload_folder = "src/main/resources/static/files/";
	// private String upload_folder_2="target/clases/static/files";

	public void saveFile(MultipartFile file, long publicacionId) throws IOException {

		if (!file.isEmpty()) {

			byte[] bytes = file.getBytes();
			Path path = Paths.get(upload_folder);
			Files.write(path, bytes);

		}

	}
}
