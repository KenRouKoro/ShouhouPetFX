package cn.korostudio.shouhoupetfx;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.resource.ClassPathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KoroStudioINFO {
    protected static Logger logger = LoggerFactory.getLogger(KoroStudioINFO.class);
    public static void LogInfo(){
        ClassPathResource classPathResource = new ClassPathResource("banner.txt");
        FileReader fileReader = new FileReader(classPathResource.getFile());
        String banner = fileReader.readString();
        logger.info(banner);
    }
}
