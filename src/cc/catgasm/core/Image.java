package cc.catgasm.core;

import cc.catgasm.core.exception.CatChainException;
import cc.catgasm.core.exception.ErrorCode;

import java.io.*;
import java.nio.file.Files;

public class Image {
    private String filename;
    private byte[] rawImage;

    public static final int MAX_FILENAME = 64; //Characters
    private static final int MAGIC_NUMBER = 0x636174;

    public Image(String filename) throws CatChainException {
        File tmp = new File(filename);
        this.filename = tmp.getName();
        try {
            rawImage = Files.readAllBytes(tmp.toPath());
        } catch (IOException e) {
            throw new CatChainException("Could not load " + filename, e, ErrorCode.IMAGE_FILE_LOAD_FAILED);
        }
    }

    public Image(byte[] rawData) throws CatChainException {
        int imageSize = 0, magic = 0;

        try(ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
            DataInputStream dis = new DataInputStream(bais))
        {
            magic = dis.readInt();
            if(magic == MAGIC_NUMBER) {
                filename = dis.readUTF();
                imageSize = dis.readInt();
                rawImage = new byte[imageSize];

                dis.readFully(rawImage, 0, imageSize);
            }
        } catch (IOException e) {
            throw new CatChainException("Could not open input stream", e, ErrorCode.IMAGE_RAW_DATA_LOAD_FAILED);
        }
    }

    public byte[] toRawData() throws CatChainException {
        try {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 DataOutputStream dos = new DataOutputStream(baos))
            {
                dos.writeInt(MAGIC_NUMBER);
                dos.writeUTF(filename);
                dos.writeInt(rawImage.length);
                dos.write(rawImage,0, rawImage.length);
                dos.flush();
                return baos.toByteArray();
            }
        } catch (IOException e) {
            throw new CatChainException("Could not open output stream", e, ErrorCode.IMAGE_RAW_DATA_WRITE_FAILED);
        }
    }

    public byte[] getRawImage() {
        return rawImage;
    }

    public String getFilename() {
        return filename;
    }
}
