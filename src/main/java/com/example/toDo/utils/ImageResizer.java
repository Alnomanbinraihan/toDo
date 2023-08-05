package com.example.toDo.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

public class ImageResizer {

    public static void resize(String inputFilePath, String outputFilePath, int width, int height) throws IOException {
        Thumbnails.of(inputFilePath).size(width, height).toFile(outputFilePath);
    }
}
