package star.demo.fastdfs.db;

import java.util.List;
import java.util.Map;

import star.demo.fastdfs.model.Img;

public interface ImgDao {

    public List<Img> findImgList(Map map);

    public Img findImg(int Id);

    public int insert(Img img);

}
