package star.demo.fastdfs.web;

import org.csource.common.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import star.demo.fastdfs.db.ImgDao;
import star.demo.fastdfs.fdfs.FdfsClient;
import star.demo.fastdfs.model.Img;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ImgController {

    @Autowired
    private ImgDao imgDao;

    @RequestMapping("/index")
    public String index(
            @RequestParam(value="page", required=false, defaultValue="1") int page,
            @RequestParam(value="pagesize", required=false, defaultValue="10") int pagesize,
            Model model
    ){
        int start = (page - 1) * pagesize;
        int end = page * pagesize;

        Map map = new HashMap();
        map.put("start", start);
        map.put("end", end);
        List<Img> list = imgDao.findImgList(map);
        model.addAttribute("list", list);
        return "index";
    }

    @RequestMapping("/upload")
    public String upload(
            MultipartFile file
    ) throws Exception {
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        NameValuePair[] meta_list = new NameValuePair[4];
        meta_list[0] = new NameValuePair("fileName", file.getOriginalFilename());
        meta_list[1] = new NameValuePair("fileLength", String.valueOf(file.getSize()));
        meta_list[2] = new NameValuePair("fileExt", ext);
        meta_list[3] = new NameValuePair("fileAuthor", "WangLiang");
        String url = FdfsClient.upload(file.getBytes(), ext, meta_list);

        Img img = new Img();
        img.setImgName(file.getOriginalFilename());
        img.setImgType(file.getContentType());
        img.setImgUrl(url);
        imgDao.insert(img);

        return "redirect:/index";
    }

}
