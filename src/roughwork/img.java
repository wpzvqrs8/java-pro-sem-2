package roughwork;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
class ImageToConsole {
    public static void main(String[] args) throws Exception {
        /*

        String s = "";
        Path f = Paths.get("C:/Users/admin/Pictures");

        Files.list(f).forEach(System.out::println);
        BufferedImage img = ImageIO.read(new File(s));

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int rgb = img.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // ANSI true-color background
                System.out.print(
                        "\u001B[48;2;"+r+";"+g+";"+b+"m  \u001B[0m"
                );
            }
            System.out.println();
        }
        * */

        Path folder = Paths.get("C:/Users/admin/Pictures");

        Files.list(folder)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        BufferedImage img = ImageIO.read(path.toFile());

                        // Skip non-image files
                        if (img == null) {
                            return;
                        }

                        System.out.println("\nIMAGE: " + path.getFileName());

                        // Scale down so huge images don't flood the terminal
                        int step = Math.max(
                                1,
                                Math.max(img.getWidth(), img.getHeight()) / 100
                        );

                        for (int y = 0; y < img.getHeight(); y += step) {
                            for (int x = 0; x < img.getWidth(); x += step) {

                                int rgb = img.getRGB(x, y);

                                int r = (rgb >> 16) & 0xFF;
                                int g = (rgb >> 8) & 0xFF;
                                int b = rgb & 0xFF;

                                System.out.printf(
                                        "\u001B[48;2;%d;%d;%dm  \u001B[0m",
                                        r, g, b
                                );
                            }
                            System.out.println();
                        }

                    } catch (Exception e) {
                        System.out.println(
                                "Could not read: " + path.getFileName()
                        );
                    }
                });
    }
}