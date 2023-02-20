package org.javaboy.vhr.controller.map;
import java.util.List;
import java.util.Map;

import org.javaboy.vhr.model.ImageBed;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.ImageBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/map/manage/xxx")//已禁用，启用则修改

public class ImageBedController {
    @Autowired
    ImageBedService imageBedService;
    @GetMapping("/")
    public List<ImageBed> getAllImages() {
        return imageBedService.getAllImages();
    }
    @PostMapping("/")
    public RespBean addImage(@RequestBody ImageBed image) {
        if (imageBedService.addImage(image) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @DeleteMapping("/{iid}")
    public RespBean deleteImage(@PathVariable Integer iid) {
        if (imageBedService.deleteImageById(iid) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.ok("删除成功!");
    }

    @DeleteMapping("/")
    public RespBean deleteImageMul(@RequestParam("ids") List<Integer> ids){
        for(int i=0;i<ids.size();i++){
            if(imageBedService.deleteImageById(ids.get(i)) != 1){
                return RespBean.ok("删除成功!");
            }
        }
        return RespBean.ok("删除成功!");
    }
}