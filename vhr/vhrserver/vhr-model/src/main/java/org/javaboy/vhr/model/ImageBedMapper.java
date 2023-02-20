package org.javaboy.vhr.model;

import java.util.List;
import org.javaboy.vhr.model.ImageBed;
public interface ImageBedMapper {
    int deleteById(Integer id);
    int insert(ImageBed record);
    ImageBed selectById(Integer id);
    int updateById(Integer id);
    List<ImageBed> getAllImages();
}
