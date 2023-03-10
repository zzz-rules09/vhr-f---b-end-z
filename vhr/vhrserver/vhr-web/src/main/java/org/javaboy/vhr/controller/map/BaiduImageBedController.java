package org.javaboy.vhr.controller.map;
import java.util.List;
import org.javaboy.vhr.model.BaiduImageBed;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.BaiduImageBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/map/manage")
public class BaiduImageBedController {
    @Autowired
    BaiduImageBedService baiduImageBedService;
    @GetMapping("/")
    public List<BaiduImageBed> getAllImages() {
        return baiduImageBedService.getAllImages();
    }
    @PostMapping("/")
    public RespBean addImage(@RequestBody BaiduImageBed image) {
        if (baiduImageBedService.addImage(image) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @DeleteMapping("/{iid}")
    public RespBean deleteImage(@PathVariable Integer iid) {
        if (baiduImageBedService.deleteImageById(iid) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deleteImageMul(@RequestParam("ids") List<Integer> ids){
        for(int i=0;i<ids.size();i++){
            if(baiduImageBedService.deleteImageById(ids.get(i)) != 1){
                return RespBean.ok("删除成功!");
            }
        }
        return RespBean.ok("删除成功!");
    }
}