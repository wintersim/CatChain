package cc.catgasm.chain;

import java.io.*;
import java.nio.file.Files;

public class Image {
    private String filename;
    private byte[] rawImage;

    public static final int MAX_FILENAME = 64; //Characters
    private static final int MAGIC_NUMBER = 0x636174;

    public Image(String filename) throws IOException {
        File tmp = new File(filename);
        this.filename = tmp.getName();
        rawImage = Files.readAllBytes(tmp.toPath());
    }

    public Image(byte[] rawData) throws IOException {
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
        }
    }

    public byte[] toRawData() throws IOException {
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
    }

    public byte[] getRawImage() {
        return rawImage;
    }

    public String getFilename() {
        return filename;
    }
}
