package it.gruppoaton.PayslipMicroservice.Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public interface FileSerialize {

    void read(FileInputStream r) throws IOException;
    void write(FileOutputStream o) throws IOException;
}
